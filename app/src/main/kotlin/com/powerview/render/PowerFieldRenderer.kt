package com.powerview.render

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Typeface
import com.powerview.PowerZonePalette

/**
 * PowerFieldRenderer — pinta el campo como Bitmap (patron ki2, igual que
 * HRView/SpeedView/TripleData). A diferencia de SpeedView/TripleData (fondo
 * transparente), aqui SI hay fondo propio: coloreado segun la zona de
 * potencia (PowerZonePalette), igual que hacia HRView con la FC.
 *
 * Icono: siempre el rayo de potencia (app de un solo proposito).
 * Tipografia: IBM Plex Sans Condensed (Regular grande, Bold pequenos).
 */
class PowerFieldRenderer(context: Context) {

    private val density = context.resources.displayMetrics.density
    private val plexBold: Typeface =
        Typeface.createFromAsset(context.assets, "fonts/ibm_plex_sans_condensed_bold.ttf")
    private val plexRegular: Typeface =
        Typeface.createFromAsset(context.assets, "fonts/ibm_plex_sans_condensed_regular.ttf")

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
    private val iconPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
    private val bigPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        typeface = plexRegular
        textAlign = Paint.Align.CENTER
    }
    private val valuePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        typeface = plexBold
        textAlign = Paint.Align.RIGHT
    }
    private val labelPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        typeface = plexBold
        textAlign = Paint.Align.RIGHT
    }

    /** Rayo de potencia (viewport 24x24). */
    private val boltPath = Path().apply {
        moveTo(13f, 2f); lineTo(4f, 14f); lineTo(11f, 14f); lineTo(10f, 22f); lineTo(20f, 10f); lineTo(13f, 10f); close()
    }

    private fun dp(v: Float) = v * density

    fun render(
        bitmap: Bitmap,
        zone: Int,
        big: String,
        upperLabel: String,
        upperValue: String,
        lowerLabel: String,
        lowerValue: String,
        textSize: Float,
    ) {
        val canvas = Canvas(bitmap)
        bitmap.eraseColor(Color.TRANSPARENT)

        val pal = PowerZonePalette.forZone(zone)
        val w = bitmap.width.toFloat()
        val h = bitmap.height.toFloat()
        val pad = dp(8f)

        bgPaint.color = pal.background
        canvas.drawRoundRect(RectF(0f, 0f, w, h), dp(8f), dp(8f), bgPaint)

        val iconSize = dp(14f)
        iconPaint.color = pal.foreground
        canvas.save()
        canvas.translate(pad, pad)
        canvas.scale(iconSize / 24f, iconSize / 24f)
        canvas.drawPath(boltPath, iconPaint)
        canvas.restore()

        val hasUpper = upperValue.isNotEmpty()
        val hasLower = lowerValue.isNotEmpty()

        valuePaint.textSize = textSize * 0.65f
        labelPaint.textSize = textSize * 0.40f
        valuePaint.color = pal.foreground
        labelPaint.color = pal.foreground

        val xRight = w - pad
        val gap = dp(2f)
        var rightColWidth = 0f

        if (hasLower || hasUpper) {
            var y = h - pad
            if (hasLower) {
                canvas.drawText(lowerValue, xRight, y, valuePaint)
                canvas.drawText(lowerLabel, xRight, y - valuePaint.textSize - gap, labelPaint)
                rightColWidth = maxOf(rightColWidth, valuePaint.measureText(lowerValue), labelPaint.measureText(lowerLabel))
                y -= valuePaint.textSize + labelPaint.textSize + gap * 3
            }
            if (hasUpper) {
                canvas.drawText(upperValue, xRight, y, valuePaint)
                canvas.drawText(upperLabel, xRight, y - valuePaint.textSize - gap, labelPaint)
                rightColWidth = maxOf(rightColWidth, valuePaint.measureText(upperValue), labelPaint.measureText(upperLabel))
            }
        }

        val yBaseline = h - pad
        bigPaint.textSize = textSize * 1.9f
        bigPaint.color = pal.foreground
        val leftAreaEnd = if (rightColWidth > 0) w - pad - rightColWidth - dp(6f) else w - pad
        val xBig = pad + (leftAreaEnd - pad) / 2f
        canvas.drawText(big, xBig, yBaseline, bigPaint)
    }
}

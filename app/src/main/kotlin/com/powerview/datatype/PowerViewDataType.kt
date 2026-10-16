package com.powerview.datatype

import android.content.Context
import android.graphics.Bitmap
import android.widget.RemoteViews
import com.powerview.R
import com.powerview.engine.PowerViewStore
import com.powerview.registry.PowerConfigStore
import com.powerview.registry.PowerConfigStore.Position
import com.powerview.registry.PowerFieldRegistry
import com.powerview.render.PowerFieldRenderer
import io.hammerhead.karooext.extension.DataTypeImpl
import io.hammerhead.karooext.internal.Emitter
import io.hammerhead.karooext.internal.ViewEmitter
import io.hammerhead.karooext.models.DataPoint
import io.hammerhead.karooext.models.DataType
import io.hammerhead.karooext.models.ShowCustomStreamState
import io.hammerhead.karooext.models.StreamState
import io.hammerhead.karooext.models.UpdateGraphicConfig
import io.hammerhead.karooext.models.ViewConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.math.roundToInt

/**
 * PowerViewDataType — clon de HRView pero para potencia: fondo coloreado por
 * zona de potencia (7 zonas), valor grande configurable entre potencia
 * actual/3s/5s/10s, y superior/inferior configurables entre todo el catalogo
 * de campos de potencia (PowerFieldRegistry).
 */
class PowerViewDataType(
    private val applicationContext: Context,
    private val store: PowerViewStore,
) : DataTypeImpl("powerview", "powerview") {

    private val scope = CoroutineScope(Dispatchers.Default)

    private data class Snapshot(
        val zone: Int,
        val big: String,
        val upperLabel: String,
        val upperValue: String,
        val lowerLabel: String,
        val lowerValue: String,
    )

    override fun startView(context: Context, config: ViewConfig, emitter: ViewEmitter) {
        emitter.onNext(UpdateGraphicConfig(showHeader = false))
        emitter.onNext(ShowCustomStreamState(message = "", color = null))

        val renderer = PowerFieldRenderer(applicationContext)
        val width = config.viewSize.first.takeIf { it > 0 } ?: 220
        val height = config.viewSize.second.takeIf { it > 0 } ?: 110
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        if (config.preview) {
            val job: Job = scope.launch { runPreview(emitter, config, renderer, bitmap) }
            emitter.setCancellable { job.cancel() }
            return
        }

        val job: Job = scope.launch {
            var last: Snapshot? = null
            while (isActive) {
                store.refreshConfigIfNeeded()
                val snap = buildSnapshot()
                if (snap != last) {
                    emitter.updateView(buildView(snap, config, renderer, bitmap))
                    last = snap
                }
                delay(when {
                    store.rideActive && !store.paused -> 1_000L
                    store.paused                      -> 3_000L
                    else                               -> 10_000L
                })
            }
        }
        emitter.setCancellable { job.cancel() }
    }

    override fun startStream(emitter: Emitter<StreamState>) {
        val job = scope.launch {
            while (isActive) {
                val bigField = store.bigField()
                val v = store.valueFor(bigField?.typeId)
                if (bigField != null && v != null) {
                    emitter.onNext(
                        StreamState.Streaming(
                            DataPoint(
                                dataTypeId = dataTypeId,
                                values = mapOf(DataType.Field.SINGLE to v),
                            )
                        )
                    )
                }
                delay(1000L)
            }
        }
        emitter.setCancellable { job.cancel() }
    }

    // ─── Construccion del snapshot ──────────────────────────────────────────

    private fun buildSnapshot(): Snapshot {
        val bigField = store.bigField()
        val upperField = store.upperField()
        val lowerField = store.lowerField()

        fun fmt(v: Double?, decimals: Int): String? = v?.let {
            if (decimals == 0) it.roundToInt().toString()
            else {
                val factor = 10.0.pow(decimals)
                "%.${decimals}f".format(Math.round(it * factor) / factor)
            }
        }

        val bigStr = bigField?.let { f -> fmt(store.valueFor(f.typeId), f.decimals) ?: "--" } ?: "--"
        val upperStr = upperField?.let { f -> fmt(store.valueFor(f.typeId), f.decimals) ?: "--" } ?: ""
        val lowerStr = lowerField?.let { f -> fmt(store.valueFor(f.typeId), f.decimals) ?: "--" } ?: ""

        return Snapshot(
            zone = store.zone,
            big = bigStr,
            upperLabel = upperField?.shortLabel ?: "",
            upperValue = upperStr,
            lowerLabel = lowerField?.shortLabel ?: "",
            lowerValue = lowerStr,
        )
    }

    // ─── Render ──────────────────────────────────────────────────────────────

    private fun buildView(
        s: Snapshot,
        config: ViewConfig,
        renderer: PowerFieldRenderer,
        bitmap: Bitmap,
    ): RemoteViews {
        renderer.render(
            bitmap = bitmap,
            zone = s.zone,
            big = s.big,
            upperLabel = s.upperLabel,
            upperValue = s.upperValue,
            lowerLabel = s.lowerLabel,
            lowerValue = s.lowerValue,
            textSize = config.textSize.toFloat(),
        )
        val v = RemoteViews(applicationContext.packageName, R.layout.view_powerview)
        v.setImageViewBitmap(R.id.iv_field, bitmap)
        return v
    }

    /**
     * Preview del editor de pantallas del Karoo: nunca dispara RideState.Recording,
     * asi que lee la configuracion directamente de PowerConfigStore (no del
     * store gateado por RideState) y cicla las 7 zonas con datos de ejemplo.
     */
    private suspend fun runPreview(
        emitter: ViewEmitter,
        config: ViewConfig,
        renderer: PowerFieldRenderer,
        bitmap: Bitmap,
    ) {
        val bigField = PowerFieldRegistry.byId(PowerConfigStore.getFieldId(applicationContext, Position.BIG))
        val upperField = PowerFieldRegistry.byId(PowerConfigStore.getFieldId(applicationContext, Position.UPPER))
        val lowerField = PowerFieldRegistry.byId(PowerConfigStore.getFieldId(applicationContext, Position.LOWER))
        val demoByZone = intArrayOf(95, 140, 190, 240, 290, 340, 410)
        var i = 0
        while (true) {
            val zone = (i % 7) + 1
            val snap = Snapshot(
                zone = zone,
                big = demoByZone[zone - 1].toString(),
                upperLabel = upperField?.shortLabel ?: "",
                upperValue = if (upperField != null) "412" else "",
                lowerLabel = lowerField?.shortLabel ?: "",
                lowerValue = if (lowerField != null) "245" else "",
            )
            emitter.updateView(buildView(snap, config, renderer, bitmap))
            delay(1200L)
            i++
        }
    }
}

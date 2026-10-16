package com.powerview.ui

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView
import com.powerview.registry.PowerConfigStore
import com.powerview.registry.PowerConfigStore.Position
import com.powerview.registry.PowerFieldRegistry

/**
 * ConfigActivity — ajustes de PowerView.
 *
 * Valor grande: SOLO 4 opciones (potencia actual, 3s, 5s, 10s) — no tiene
 * sentido un numero grande mostrando "potencia media de toda la actividad",
 * eso es lo que van las posiciones pequeñas.
 * Superior / Inferior: todo el catalogo de potencia (PowerFieldRegistry.ALL).
 *
 * Por defecto: grande = potencia 3s, superior = potencia máxima (actividad),
 * inferior = potencia media (actividad) — ver PowerConfigStore.Position.
 */
class ConfigActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val density = resources.displayMetrics.density
        fun dp(v: Int) = (v * density).toInt()

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(24), dp(20), dp(24))
            setBackgroundColor(0xFF111111.toInt())
        }

        root.addView(TextView(this).apply {
            text = "PowerView — Configuración"
            textSize = 20f
            setTextColor(0xFFFFFFFF.toInt())
            setPadding(0, 0, 0, dp(8))
        })

        root.addView(TextView(this).apply {
            text = "El valor grande solo admite potencia actual o suavizada. Superior e inferior aceptan cualquier campo de potencia."
            textSize = 13f
            setTextColor(0xFFAAAAAA.toInt())
            setPadding(0, 0, 0, dp(20))
        })

        val bigLabels = PowerFieldRegistry.BIG_OPTIONS.map { it.label }
        val bigIds = PowerFieldRegistry.BIG_OPTIONS.map { it.id }
        addFieldRow(root, "Valor grande", bigLabels, bigIds, Position.BIG)

        val allLabels = listOf("Sin usar") + PowerFieldRegistry.ALL.map { it.label }
        val allIds = listOf<String?>(null) + PowerFieldRegistry.ALL.map { it.id }
        addFieldRow(root, "Superior", allLabels, allIds, Position.UPPER)
        addFieldRow(root, "Inferior", allLabels, allIds, Position.LOWER)

        setContentView(ScrollView(this).apply { addView(root) })
    }

    private fun addFieldRow(
        root: LinearLayout,
        rowLabel: String,
        labels: List<String>,
        ids: List<String?>,
        position: Position,
    ) {
        val density = resources.displayMetrics.density
        fun dp(v: Int) = (v * density).toInt()

        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(0, dp(10), 0, dp(10))
        }

        row.addView(TextView(this).apply {
            text = rowLabel
            textSize = 14f
            setTextColor(0xFFFFFFFF.toInt())
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        })

        val spinner = Spinner(this).apply {
            adapter = ArrayAdapter(this@ConfigActivity, android.R.layout.simple_spinner_dropdown_item, labels)
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.4f)
        }

        val currentId = PowerConfigStore.getFieldId(this, position)
        val currentIndex = ids.indexOf(currentId).let { if (it < 0) 0 else it }
        spinner.setSelection(currentIndex, false)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                PowerConfigStore.setFieldId(this@ConfigActivity, position, ids[pos])
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        row.addView(spinner)
        root.addView(row)
    }
}

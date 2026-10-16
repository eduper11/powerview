package com.powerview.registry

import android.content.Context

/**
 * PowerConfigStore — persiste los 3 campos configurados para PowerView: valor
 * grande, superior e inferior. A diferencia de TripleData, aqui solo hay UN
 * campo (PowerView), no 10 slots — por eso no hace falta indexar por numero.
 *
 * Valores por defecto (definidos en getFieldId con fallback): grande =
 * potencia 3s, superior = potencia máxima (actividad), inferior = potencia
 * media (actividad) — los que pediste como configuracion inicial.
 */
object PowerConfigStore {

    private const val PREFS_NAME = "powerview_config"

    enum class Position(val key: String, val default: String) {
        BIG("big", "power_3s"),
        UPPER("upper", "max_power"),
        LOWER("lower", "avg_power"),
    }

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getFieldId(context: Context, position: Position): String? =
        prefs(context).getString(position.key, position.default)

    fun setFieldId(context: Context, position: Position, fieldId: String?) {
        prefs(context).edit().apply {
            if (fieldId == null) remove(position.key) else putString(position.key, fieldId)
        }.apply()
    }
}

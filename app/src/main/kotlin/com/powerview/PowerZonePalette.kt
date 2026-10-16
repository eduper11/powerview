package com.powerview

import android.graphics.Color

data class ZoneColors(val background: Int, val foreground: Int)

/**
 * PowerZonePalette — colores de fondo por zona de potencia (7 zonas, modelo
 * Coggan que usa Karoo).
 *
 * Paleta calcada de la nativa de Karoo OS. Fuente: Barberfish (extension
 * open-source para Karoo que replica el aspecto nativo), cuyo README describe
 * explicitamente su paleta "Karoo" como la que "matches the colors on the
 * Karoo's own zone settings screens". Los 7 valores se extrajeron del SVG de
 * su documentacion (docs/img/palette-power-karoo.svg, fila "Fill mode" —
 * colores de marca sin ajustar por contraste):
 * https://github.com/jpweytjens/Barberfish
 *
 * Z1 y Z2 son ambos verdes (uno oscuro, uno claro) — coincide con quejas de
 * usuarios de que Karoo "comparte el verde" entre las dos primeras zonas.
 * Colores de texto elegidos por luminancia relativa de cada fondo (blanco
 * sobre fondos oscuros, negro sobre fondos claros).
 */
object PowerZonePalette {
    private val DARK = Color.parseColor("#111111")
    private val LIGHT = Color.parseColor("#FFFFFF")

    private val Z1 = Color.parseColor("#1A8C3A") // recuperación activa — verde oscuro
    private val Z2 = Color.parseColor("#40D078") // resistencia — verde claro
    private val Z3 = Color.parseColor("#F0D800") // tempo — amarillo
    private val Z4 = Color.parseColor("#F08868") // umbral — salmón
    private val Z5 = Color.parseColor("#F06020") // VO2max — naranja
    private val Z6 = Color.parseColor("#D01020") // anaeróbico — rojo
    private val Z7 = Color.parseColor("#9020A0") // neuromuscular — morado

    fun forZone(zone: Int): ZoneColors = when (zone) {
        1    -> ZoneColors(Z1, LIGHT)
        2    -> ZoneColors(Z2, DARK)
        3    -> ZoneColors(Z3, DARK)
        4    -> ZoneColors(Z4, DARK)
        5    -> ZoneColors(Z5, LIGHT)
        6    -> ZoneColors(Z6, LIGHT)
        7    -> ZoneColors(Z7, LIGHT)
        else -> ZoneColors(LIGHT, DARK) // 0 = sin dato todavia (no confundir con Z1)
    }
}

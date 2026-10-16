package com.powerview.registry

import io.hammerhead.karooext.models.DataType

/**
 * PowerFieldRegistry — catalogo de campos SOLO de potencia, seleccionables
 * para PowerView. A diferencia de TripleData (catalogo general de ~115
 * campos), aqui unicamente se ofrecen metricas relacionadas con potencia:
 * potencia y sus ventanas de suavizado, medias/maximas de actividad y de
 * vuelta, potencia normalizada, potencia/peso, zona, %FTP, par motor y
 * balance de pedaleo.
 *
 * eligibleForBig marca los 4 campos que aparecen en el selector del VALOR
 * GRANDE (potencia actual, 3s, 5s, 10s) — el resto del catalogo solo esta
 * disponible para las posiciones superior e inferior.
 *
 * NOTA: karoo-ext no tiene un tipo "potencia 20s"; las ventanas de suavizado
 * de potencia confirmadas en la Dokka son 3s, 5s, 10s, 30s, 20min y 1h. Por
 * decision explicita, la cuarta opcion del campo grande usa 10s.
 *
 * Todas las constantes referencian DataType.Type directamente, verificadas
 * contra la Dokka oficial de karoo-ext 1.1.9.
 */

enum class MetricIcon { POWER, GENERIC }

data class FieldOption(
    val id: String,
    val label: String,
    val shortLabel: String,
    val typeId: String,
    val decimals: Int = 0,
    val icon: MetricIcon = MetricIcon.POWER,
    val eligibleForBig: Boolean = false,
)

object PowerFieldRegistry {

    val ALL: List<FieldOption> = listOf(
        FieldOption("power", "Potencia actual", "POT", DataType.Type.POWER, decimals = 0, icon = MetricIcon.POWER, eligibleForBig = true),
        FieldOption("power_3s", "Potencia (3s)", "POT 3S", DataType.Type.SMOOTHED_3S_AVERAGE_POWER, decimals = 0, icon = MetricIcon.POWER, eligibleForBig = true),
        FieldOption("power_5s", "Potencia (5s)", "POT 5S", DataType.Type.SMOOTHED_5S_AVERAGE_POWER, decimals = 0, icon = MetricIcon.POWER, eligibleForBig = true),
        FieldOption("power_10s", "Potencia (10s)", "POT 10S", DataType.Type.SMOOTHED_10S_AVERAGE_POWER, decimals = 0, icon = MetricIcon.POWER, eligibleForBig = true),
        FieldOption("power_30s", "Potencia (30s)", "POT 30S", DataType.Type.SMOOTHED_30S_AVERAGE_POWER, decimals = 0, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("power_20m", "Potencia (20 min)", "POT 20M", DataType.Type.SMOOTHED_20M_AVERAGE_POWER, decimals = 0, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("power_1hr", "Potencia (1 hora)", "POT 1H", DataType.Type.SMOOTHED_1HR_AVERAGE_POWER, decimals = 0, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("avg_power", "Potencia media (actividad)", "POT MED", DataType.Type.AVERAGE_POWER, decimals = 0, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("max_power", "Potencia máxima (actividad)", "POT MÁX", DataType.Type.MAX_POWER, decimals = 0, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("power_lap", "Potencia media (esta vuelta)", "POT MED L", DataType.Type.POWER_LAP, decimals = 0, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("max_power_lap", "Potencia máxima (esta vuelta)", "POT MÁX L", DataType.Type.MAX_POWER_LAP, decimals = 0, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("avg_power_last_lap", "Potencia media (vuelta ant.)", "POT MED VA", DataType.Type.AVERAGE_POWER_LAST_LAP, decimals = 0, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("max_power_last_lap", "Potencia máxima (vuelta ant.)", "POT MÁX VA", DataType.Type.MAX_POWER_LAST_LAP, decimals = 0, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("normalized_power", "Potencia normalizada (actividad)", "NP", DataType.Type.NORMALIZED_POWER, decimals = 0, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("normalized_power_lap", "Pot. normalizada (esta vuelta)", "NP L", DataType.Type.NORMALIZED_POWER_LAP, decimals = 0, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("normalized_power_last_lap", "Pot. normalizada (vuelta ant.)", "NP VA", DataType.Type.NORMALIZED_POWER_LAST_LAP, decimals = 0, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("power_to_weight", "Potencia/peso (actividad)", "W/KG", DataType.Type.POWER_TO_WEIGHT, decimals = 2, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("power_to_weight_lap", "Potencia/peso (esta vuelta)", "W/KG L", DataType.Type.POWER_TO_WEIGHT_LAP, decimals = 2, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("power_to_weight_last_lap", "Potencia/peso (vuelta ant.)", "W/KG VA", DataType.Type.POWER_TO_WEIGHT_LAST_LAP, decimals = 2, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("power_zone", "Zona de potencia", "Z.POT", DataType.Type.POWER_ZONE, decimals = 0, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("percent_max_ftp", "% FTP", "%FTP", DataType.Type.PERCENT_MAX_FTP, decimals = 0, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("intensity_factor", "Intensity Factor", "IF", DataType.Type.INTENSITY_FACTOR, decimals = 2, icon = MetricIcon.GENERIC, eligibleForBig = false),
        FieldOption("avg_torque", "Par motor medio (actividad)", "PAR MED", DataType.Type.AVERAGE_TORQUE, decimals = 1, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("max_torque", "Par motor máximo (actividad)", "PAR MÁX", DataType.Type.MAX_TORQUE, decimals = 1, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("max_torque_lap", "Par motor máximo (esta vuelta)", "PAR MÁX L", DataType.Type.MAX_TORQUE_LAP, decimals = 1, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("torque_3s", "Par motor (3s)", "PAR 3S", DataType.Type.SMOOTHED_3S_AVERAGE_TORQUE, decimals = 1, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("pedal_power_balance", "Balance de pedaleo", "BAL", DataType.Type.PEDAL_POWER_BALANCE, decimals = 0, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("avg_pedal_power_balance", "Balance pedaleo medio (actividad)", "BAL MED", DataType.Type.AVERAGE_PEDAL_POWER_BALANCE, decimals = 0, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("pedal_power_balance_3s", "Balance pedaleo (3s)", "BAL 3S", DataType.Type.SMOOTHED_3S_AVERAGE_PEDAL_POWER_BALANCE, decimals = 0, icon = MetricIcon.POWER, eligibleForBig = false),
        FieldOption("pedal_power_balance_10s", "Balance pedaleo (10s)", "BAL 10S", DataType.Type.SMOOTHED_10S_AVERAGE_PEDAL_POWER_BALANCE, decimals = 0, icon = MetricIcon.POWER, eligibleForBig = false),
    )

    val BIG_OPTIONS: List<FieldOption> = ALL.filter { it.eligibleForBig }

    fun byId(id: String?): FieldOption? = ALL.firstOrNull { it.id == id }
}

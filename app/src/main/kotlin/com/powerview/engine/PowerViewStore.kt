package com.powerview.engine

import android.content.Context
import android.util.Log
import com.powerview.registry.PowerConfigStore
import com.powerview.registry.PowerConfigStore.Position
import com.powerview.registry.PowerFieldRegistry
import io.hammerhead.karooext.KarooSystemService
import io.hammerhead.karooext.models.DataType
import io.hammerhead.karooext.models.OnStreamState
import io.hammerhead.karooext.models.RideState
import io.hammerhead.karooext.models.StreamState

/**
 * PowerViewStore — suscribe la zona de potencia (para el fondo) y los 3
 * campos configurados (grande/superior/inferior), deduplicando si dos
 * posiciones comparten el mismo typeId. Todo gated por RideState.Recording,
 * igual que HRView/SpeedView/TripleData.
 */
class PowerViewStore(private val context: Context) {

    companion object { private const val TAG = "PowerViewStore" }

    private val values = mutableMapOf<String, Double?>()
    @Volatile var zone: Int = 0; private set

    private var currentBigId: String? = null
    private var currentUpperId: String? = null
    private var currentLowerId: String? = null

    @Volatile var rideActive: Boolean = false; private set
    @Volatile var paused: Boolean = false; private set

    private var rideConsumerId: String? = null
    private val sensorConsumerIds = mutableMapOf<String, String>()
    private var karooSystemRef: KarooSystemService? = null

    fun connect(ks: KarooSystemService) {
        karooSystemRef = ks
        rideConsumerId = ks.addConsumer { state: RideState ->
            when (state) {
                is RideState.Recording -> {
                    paused = false
                    if (!rideActive) {
                        rideActive = true
                        values.clear()
                        zone = 0
                        subscribeConfiguredFields(ks)
                        Log.d(TAG, "ride started")
                    }
                }
                is RideState.Paused -> { paused = true }
                is RideState.Idle -> {
                    rideActive = false
                    paused = false
                    unsubscribeAll(ks)
                    values.clear()
                    zone = 0
                    Log.d(TAG, "ride idle")
                }
            }
        }
    }

    fun disconnect(ks: KarooSystemService) {
        unsubscribeAll(ks)
        rideConsumerId?.let { ks.removeConsumer(it) }
        rideConsumerId = null
        karooSystemRef = null
    }

    fun refreshConfigIfNeeded() {
        val ks = karooSystemRef ?: return
        if (!rideActive) return
        val bigId = PowerConfigStore.getFieldId(context, Position.BIG)
        val upperId = PowerConfigStore.getFieldId(context, Position.UPPER)
        val lowerId = PowerConfigStore.getFieldId(context, Position.LOWER)
        if (bigId != currentBigId || upperId != currentUpperId || lowerId != currentLowerId) {
            unsubscribeAll(ks)
            subscribeConfiguredFields(ks)
        }
    }

    fun bigField() = PowerFieldRegistry.byId(currentBigId)
    fun upperField() = PowerFieldRegistry.byId(currentUpperId)
    fun lowerField() = PowerFieldRegistry.byId(currentLowerId)
    fun valueFor(typeId: String?): Double? = typeId?.let { values[it] }

    private fun subscribeConfiguredFields(ks: KarooSystemService) {
        currentBigId = PowerConfigStore.getFieldId(context, Position.BIG)
        currentUpperId = PowerConfigStore.getFieldId(context, Position.UPPER)
        currentLowerId = PowerConfigStore.getFieldId(context, Position.LOWER)

        // Zona de potencia, siempre suscrita — manda el color de fondo.
        sensorConsumerIds[DataType.Type.POWER_ZONE] = ks.addConsumer(
            OnStreamState.StartStreaming(DataType.Type.POWER_ZONE)
        ) { e: OnStreamState -> zone = (e.doubleOrNull()?.toInt() ?: 0).coerceIn(0, 7) }

        val fields = listOfNotNull(
            PowerFieldRegistry.byId(currentBigId),
            PowerFieldRegistry.byId(currentUpperId),
            PowerFieldRegistry.byId(currentLowerId),
        )
        fields.map { it.typeId }.distinct().forEach { typeId ->
            if (sensorConsumerIds.containsKey(typeId)) return@forEach // ya suscrito (p.ej. == POWER_ZONE)
            sensorConsumerIds[typeId] = ks.addConsumer(
                OnStreamState.StartStreaming(typeId)
            ) { e: OnStreamState -> values[typeId] = e.doubleOrNull() }
        }
    }

    private fun unsubscribeAll(ks: KarooSystemService) {
        sensorConsumerIds.values.forEach { ks.removeConsumer(it) }
        sensorConsumerIds.clear()
    }

    private fun OnStreamState.doubleOrNull(): Double? {
        val s = state
        return if (s is StreamState.Streaming)
            s.dataPoint.singleValue?.takeIf { it.isFinite() }
        else null
    }
}

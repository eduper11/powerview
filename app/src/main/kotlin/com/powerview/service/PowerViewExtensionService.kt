package com.powerview.service

import android.util.Log
import com.powerview.BuildConfig
import com.powerview.datatype.PowerViewDataType
import com.powerview.engine.PowerViewStore
import io.hammerhead.karooext.KarooSystemService
import io.hammerhead.karooext.extension.DataTypeImpl
import io.hammerhead.karooext.extension.KarooExtension

class PowerViewExtensionService : KarooExtension("powerview", BuildConfig.VERSION_NAME) {

    companion object { private const val TAG = "PowerViewExtService" }

    private lateinit var karooSystem: KarooSystemService
    private val store by lazy { PowerViewStore(applicationContext) }

    override val types: List<DataTypeImpl> by lazy {
        listOf(PowerViewDataType(applicationContext, store))
    }

    override fun onCreate() {
        super.onCreate()
        karooSystem = KarooSystemService(applicationContext)
        karooSystem.connect { connected ->
            Log.d(TAG, "Karoo system connected=$connected")
            if (connected) store.connect(karooSystem)
        }
    }

    override fun onDestroy() {
        if (::karooSystem.isInitialized) {
            store.disconnect(karooSystem)
            karooSystem.disconnect()
        }
        super.onDestroy()
    }
}

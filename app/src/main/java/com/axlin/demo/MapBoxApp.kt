package com.axlin.demo

import android.app.Application
import com.mapbox.mapboxsdk.Mapbox

class MapBoxApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Mapbox.getInstance(applicationContext, getString(R.string.access_token))
    }
}
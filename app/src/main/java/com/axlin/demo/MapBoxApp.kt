package com.axlin.demo

import android.app.Application
import android.content.Context
import com.axlin.demo.utils.Tools
import com.mapbox.mapboxsdk.Mapbox

class MapBoxApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Mapbox.getInstance(applicationContext, getString(R.string.access_token))
        Tools.application = this
        Tools.sharedPreferences = getSharedPreferences("canada", Context.MODE_PRIVATE)
    }
}
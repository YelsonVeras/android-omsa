package com.alhaythamapps.mapboxoid

import android.app.Application
import com.mapbox.mapboxsdk.Mapbox

class MapboxoidApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(applicationContext, getString(R.string.access_token))
    }
}
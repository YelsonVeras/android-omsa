package com.alhaythamapps.mapboxoid

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style


class MainActivity : AppCompatActivity() {
    private var mapView: MapView? = null
    private var mapbox: MapboxMap? = null
    private var permissionsManager: PermissionsManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapView = findViewById(R.id.mapView)
        mapView?.onCreate(savedInstanceState)

        mapView?.getMapAsync { mapbox ->
            this.mapbox = mapbox

            mapbox.setStyle(Style.MAPBOX_STREETS) { style ->
                enableLocation(style)
            }
        }
    }

    private fun enableLocation(style: Style) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            mapbox?.locationComponent?.activateLocationComponent(
                LocationComponentActivationOptions.builder(this, style).build()
            )
            mapbox?.locationComponent?.isLocationComponentEnabled = true
            mapbox?.locationComponent?.cameraMode = CameraMode.TRACKING
            mapbox?.locationComponent?.renderMode = RenderMode.COMPASS
        } else {
            permissionsManager = PermissionsManager(LocationPermissionsListener()).apply {
                requestLocationPermissions(
                    this@MainActivity
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissionsManager?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    inner class LocationPermissionsListener : PermissionsListener {
        override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
            Toast.makeText(
                this@MainActivity,
                R.string.location_permissions_explanation,
                Toast.LENGTH_LONG
            )
                .show()
        }

        override fun onPermissionResult(granted: Boolean) {
            if (granted) {
                mapbox?.getStyle() { style ->
                    enableLocation(style)
                }
            } else {
                Toast.makeText(
                    this@MainActivity,
                    R.string.location_permissions_not_granted,
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()

        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()

        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()

        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()

        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()

        mapView?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        mapView?.onSaveInstanceState(outState)
    }
}

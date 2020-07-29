package com.axlin.demo.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.axlin.demo.R
import com.axlin.demo.models.responses.StationResponse
import com.axlin.demo.network.RestService
import com.axlin.demo.tasks.BuildStationTask
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style


class MainActivity : AppCompatActivity() {

    private var mapView: MapView? = null
    private var mapBox: MapboxMap? = null

    private var permissionsManager: PermissionsManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapView = findViewById(R.id.mapView)
        mapView?.onCreate(savedInstanceState)

        mapView?.getMapAsync { mapBox ->
            this.mapBox = mapBox
            mapBox.setStyle(Style.MAPBOX_STREETS) { style ->
                enableLocation(style)
                enabledGestures()
//                addMarkStation()
                RestService.service
                    .getStations()
                    .enqueue(BuildStationTask(this))
            }
        }
    }

    private fun enableLocation(style: Style) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            mapBox?.locationComponent?.activateLocationComponent(
                LocationComponentActivationOptions.builder(this, style).build()
            )
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            mapBox?.locationComponent?.isLocationComponentEnabled = true
            mapBox?.locationComponent?.cameraMode = CameraMode.TRACKING_GPS_NORTH
            mapBox?.locationComponent?.renderMode = RenderMode.COMPASS
        } else {
            permissionsManager = PermissionsManager(LocationPermissionsListener()).apply {
                requestLocationPermissions(
                    this@MainActivity
                )
            }
        }
    }

    private fun enabledGestures() {
        mapBox?.uiSettings?.isDoubleTapGesturesEnabled = false
    }

    fun addMarkStation(stations: List<StationResponse>?) {
        stations?.forEach {
            mapBox?.addMarker(
                MarkerOptions()
                    .position(LatLng(it.latitud.toDouble(), it.longitud.toDouble()))
                    .title(it.address)
            )
        }
        mapBox?.addMarker(
            MarkerOptions()
                .position(LatLng(19.429094, -70.694826))
                .title("Los jasmines Station")
        )
        mapBox?.setOnMarkerClickListener { marker ->
            makeText(this, marker.title, Toast.LENGTH_LONG).show()
            true
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
            makeText(
                this@MainActivity,
                R.string.location_permissions_explanation,
                Toast.LENGTH_LONG
            ).show()
        }

        override fun onPermissionResult(granted: Boolean) {
            if (granted) {
                mapBox?.getStyle() { style ->
                    enableLocation(style)
                }
            } else {
                makeText(
                    this@MainActivity,
                    R.string.location_permissions_not_granted,
                    Toast.LENGTH_LONG
                ).show()
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

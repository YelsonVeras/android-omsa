package com.axlin.demo.tasks

import android.widget.Toast
import com.axlin.demo.activities.MainActivity
import com.axlin.demo.models.interfaces.UiNotifiers
import com.axlin.demo.models.responses.RouteResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuildRouteTask(private val mainActivity: MainActivity) : Callback<List<RouteResponse>> {

    override fun onResponse(
        call: Call<List<RouteResponse>>,
        response: Response<List<RouteResponse>>
    ) {
        try {
            if (response.isSuccessful) {
                mainActivity.setRoute(response.body())
            } else {
                UiNotifiers.toast("La respuesta del servidor no es correspondida", mainActivity)
            }
        } catch (e: Exception) {
            UiNotifiers.alert("Error", mainActivity)
        }
    }

    override fun onFailure(call: Call<List<RouteResponse>?>, t: Throwable) {
        Toast.makeText(mainActivity, t.message, Toast.LENGTH_LONG).show()
    }
}
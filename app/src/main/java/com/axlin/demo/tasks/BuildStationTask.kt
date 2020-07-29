package com.axlin.demo.tasks

import android.widget.Toast
import com.axlin.demo.activities.MainActivity
import com.axlin.demo.models.interfaces.UiNotifiers
import com.axlin.demo.models.responses.StationResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuildStationTask(private val mainActivity: MainActivity) : Callback<List<StationResponse>> {

    override fun onResponse(
        call: Call<List<StationResponse>>,
        response: Response<List<StationResponse>>
    ) {
        try {
            if (response.isSuccessful) {
                mainActivity.addMarkStation(response.body())
            } else {
                UiNotifiers.toast("La respuesta del servidor no es correspondida", mainActivity)
            }
        } catch (e: Exception) {
            UiNotifiers.alert("Error", mainActivity)
        }
    }

    override fun onFailure(call: Call<List<StationResponse>?>, t: Throwable) {
        Toast.makeText(mainActivity, t.message, Toast.LENGTH_LONG).show()
    }
}
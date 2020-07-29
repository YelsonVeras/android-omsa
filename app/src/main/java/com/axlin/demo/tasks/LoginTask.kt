package com.axlin.demo.tasks

import android.content.Intent
import android.widget.Toast
import com.axlin.demo.activities.LoginActivity
import com.axlin.demo.activities.MainActivity
import com.axlin.demo.models.interfaces.UiNotifiers
import com.axlin.demo.models.responses.PersonResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginTask(private val loginActivity: LoginActivity) : Callback<PersonResponse> {

    override fun onResponse(call: Call<PersonResponse>, response: Response<PersonResponse>) {
        try {
            if (response.isSuccessful) {
//                SharedPrefManager.getInstance(Tools.application).saveToken(response.body())
                val intent = Intent(loginActivity, MainActivity::class.java)
                loginActivity.startActivity(intent)
            } else {
                UiNotifiers.toast("La respuesta del servidor no es correspondida", loginActivity)
                loginActivity.setInteraction(true)
            }
        } catch (e: Exception) {
            UiNotifiers.alert("Login Error", loginActivity)
            loginActivity.setInteraction(true)
        }
    }

    override fun onFailure(call: Call<PersonResponse?>, t: Throwable) {
        Toast.makeText(loginActivity, t.message, Toast.LENGTH_LONG).show()
        loginActivity.setInteraction(true)
    }
}
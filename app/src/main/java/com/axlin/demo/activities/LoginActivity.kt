package com.axlin.demo.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.axlin.demo.R
import com.axlin.demo.models.requests.PersonRequest
import com.axlin.demo.network.RestService
import com.axlin.demo.tasks.LoginTask
import kotlinx.android.synthetic.main.layout_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        cirLoginButton.setOnClickListener {
            setInteraction(false)
            executeLogin()
        }

        editTextUsername.setText("yelson")
        editTextPassword.setText("monica")
    }

    override fun onResume() {
        super.onResume()
        setInteraction(true)
    }

    private fun executeLogin() {
        RestService.service
            .login(
                PersonRequest(
                    "",
                    editTextUsername.text.toString(),
                    editTextPassword.text.toString()
                )
            )
            .enqueue(LoginTask(this@LoginActivity))
    }

    fun setInteraction(enabled: Boolean) {
        cirLoginButton.isEnabled = enabled
        editTextUsername.isEnabled = enabled
        editTextPassword.isEnabled = enabled
    }
}
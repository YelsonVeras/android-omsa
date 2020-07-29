package com.drts.pizzaApp.storage

import android.content.Context

class SharedPrefManager private constructor(private val mCtx: Context) {

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences =
                mCtx.getSharedPreferences(SHARED_PREF_TOKEN, Context.MODE_PRIVATE)
            return sharedPreferences.getInt("id", -1) != -1
        }

    val token: String?
        get() {
            return mCtx.getSharedPreferences(SHARED_PREF_TOKEN, Context.MODE_PRIVATE)
                .getString("token", "");
        }

    fun saveToken(token: String) {
        mCtx.getSharedPreferences(SHARED_PREF_TOKEN, Context.MODE_PRIVATE).edit()
            .putString("token", token).apply()
    }

    fun clear() {
        mCtx.getSharedPreferences(SHARED_PREF_TOKEN, Context.MODE_PRIVATE).edit().clear().apply()
    }

    companion object {
        private const val SHARED_PREF_TOKEN = "my_token"
        private const val SHARED_PREF_ID_USER = "id_user"

        private var mInstance: SharedPrefManager? = null

        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }
}
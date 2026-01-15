package com.example.verv

import android.content.Context
import android.content.SharedPreferences

object UserRepository {
    private const val PREF_NAME = "user_pref"
    private const val KEY_NAME = "name"
    private const val KEY_EMAIL = "email"
    private const val KEY_PASSWORD = "password"
    private const val KEY_PHONE = "phone"
    private const val KEY_LOCATION = "location"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUser(context: Context, user: User) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_NAME, user.name)
        editor.putString(KEY_EMAIL, user.email)
        editor.putString(KEY_PASSWORD, user.password)
        editor.putString(KEY_PHONE, user.phone)
        editor.putString(KEY_LOCATION, user.location)
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.apply()
    }

    fun getUser(context: Context): User? {
        val prefs = getSharedPreferences(context)
        val isLoggedIn = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
        if (!isLoggedIn) {
            return null
        }
        return User(
            name = prefs.getString(KEY_NAME, "") ?: "",
            email = prefs.getString(KEY_EMAIL, "") ?: "",
            password = prefs.getString(KEY_PASSWORD, "") ?: "",
            phone = prefs.getString(KEY_PHONE, "") ?: "",
            location = prefs.getString(KEY_LOCATION, "") ?: ""
        )
    }

    fun logoutUser(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.clear()
        editor.apply()
    }
}

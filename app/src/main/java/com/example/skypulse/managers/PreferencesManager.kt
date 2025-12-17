package com.example.skypulse.managers

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object PreferencesManager {
    private const val PREF_NAME = "skypulse_settings"
    private lateinit var prefs: SharedPreferences

    fun initialize(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getStringPreference(key: String): String? {
        return prefs.getString(key, null)
    }

    fun setStringPreference(key: String, value: String) {
        prefs.edit {
            putString(key, value)
        }
    }
}
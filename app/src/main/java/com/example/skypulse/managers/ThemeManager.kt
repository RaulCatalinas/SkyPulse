package com.example.skypulse.managers

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoMode
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.skypulse.enums.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.lang.ref.WeakReference

/**
 * Singleton manager for theme preferences
 * Keeps theme in memory and only persists to disk when needed
 * Uses WeakReference to avoid memory leaks
 */
object ThemeManager {
    // Extension property for DataStore
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val THEME_KEY = stringPreferencesKey("theme_mode")

    // In-memory theme state
    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)

    // Weak reference to context to avoid memory leaks
    private var contextRef: WeakReference<Context>? = null

    private var isInitialized = false

    /**
     * Initialize the theme manager and load saved theme
     * Call this from Application.onCreate() with applicationContext
     *
     * IMPORTANT: Pass applicationContext, NOT activity context
     */
    suspend fun initialize(appContext: Context) {
        if (isInitialized) return

        // Store weak reference to application context (safe from memory leaks)
        contextRef = WeakReference(appContext.applicationContext)

        // Load saved theme from DataStore
        val savedTheme = appContext.applicationContext.dataStore.data
            .map { preferences ->
                ThemeMode.fromString(preferences[THEME_KEY])
            }
            .first()

        _themeMode.value = savedTheme
        isInitialized = true
    }

    /**
     * Set theme mode (in memory only)
     * Will be persisted when saveTheme() is called
     */
    fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
    }

    /**
     * Save current theme to disk
     * Call this from Activity.onStop() or onDestroy()
     * Pass the context as parameter to avoid storing it
     */
    suspend fun saveTheme(context: Context) {
        if (!isInitialized) return

        context.applicationContext.dataStore.edit { preferences ->
            preferences[THEME_KEY] = _themeMode.value.name
        }
    }

    /**
     * Clear references (optional, for cleanup)
     */
    fun cleanup() {
        contextRef?.clear()
        contextRef = null
    }

    fun getIconTheme(): ImageVector {
        return when (_themeMode.value) {
            ThemeMode.DARK -> Icons.Filled.LightMode
            ThemeMode.LIGHT -> Icons.Filled.DarkMode
            ThemeMode.SYSTEM -> Icons.Filled.AutoMode
        }
    }
}
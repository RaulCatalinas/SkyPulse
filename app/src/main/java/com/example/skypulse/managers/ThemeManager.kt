package com.example.skypulse.managers

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoMode
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.skypulse.enums.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.ref.WeakReference

/**
 * Singleton manager for theme preferences
 * Keeps theme in memory and only persists to disk when needed
 * Uses WeakReference to avoid memory leaks
 */
object ThemeManager {
    // Extension property for DataStore
    private const val THEME_KEY = "theme_mode"

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
    fun initialize(appContext: Context) {
        if (isInitialized) return

        // Store weak reference to application context (safe from memory leaks)
        contextRef = WeakReference(appContext.applicationContext)

        val themeFromPreferences = PreferencesManager.getStringPreference(THEME_KEY)

        // Load saved theme from DataStore
        _themeMode.value = ThemeMode.fromString(themeFromPreferences)
        isInitialized = true
    }

    /**
     * Set theme mode
     */
    fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
        PreferencesManager.setStringPreference(THEME_KEY, _themeMode.value.name.lowercase())
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
package com.example.skypulse

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.example.skypulse.managers.ThemeManager
import com.example.skypulse.services.WeatherService
import com.example.skypulse.ui.screens.HomeScreen
import com.example.skypulse.ui.theme.SkyPulseTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            ThemeManager.initialize(applicationContext)
        }
        setContent {
            SkyPulseTheme {
                HomeScreen()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        WeatherService.closeClient()
        ThemeManager.cleanup()
    }

    override fun onStop() {
        super.onStop()

        lifecycleScope.launch {
            ThemeManager.saveTheme(applicationContext)
        }
    }
}
package com.example.skypulse

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.skypulse.services.WeatherService
import com.example.skypulse.ui.screens.HomeScreen
import com.example.skypulse.ui.theme.SkyPulseTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkyPulseTheme {
                HomeScreen()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        WeatherService.closeClient()
    }
}
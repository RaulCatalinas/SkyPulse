package com.example.skypulse

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.skypulse.services.WeatherService
import com.example.skypulse.ui.screens.HomeScreen
import com.example.skypulse.ui.screens.SettingsScreen
import com.example.skypulse.ui.theme.SkyPulseTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkyPulseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    SettingsScreen.createUI()
                    HomeScreen(onSettingsClick = SettingsScreen::toggleDrawerState)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        WeatherService.closeClient()
    }
}
package com.example.skypulse.ui.screens

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.example.skypulse.ui.screens.settings.SettingsContentView
import kotlinx.coroutines.launch

/**
 * Component responsible for ALL configuration logic
 * Manages the drawer state and its content
 */
object SettingsScreen {
    /**
     * Main composable that wraps the content with the drawer
     *
     * @param content Composable that will be the main content (HomeScreen)
     */
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun WithDrawer(content: @Composable (onSettingsClick: () -> Unit) -> Unit) {
        // Drawer state
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        // Handler to open/close the drawer
        val toggleDrawer: () -> Unit = {
            scope.launch {
                if (drawerState.isClosed) drawerState.open()
                else drawerState.close()
            }
        }

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                SettingsContentView()
            }
        ) {
            // Pass the settings callback to the main content
            content(toggleDrawer)
        }
    }
}
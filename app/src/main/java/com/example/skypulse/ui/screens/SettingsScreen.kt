package com.example.skypulse.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.skypulse.components.common.CreateIcon
import com.example.skypulse.components.common.CreateText
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
                DrawerContent(
                    onDrawerClose = { scope.launch { drawerState.close() } }
                )
            }
        ) {
            // Pass the settings callback to the main content
            content(toggleDrawer)
        }
    }

    /**
     * Settings drawer content
     */
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun DrawerContent(
        onDrawerClose: () -> Unit = {}
    ) {
        ModalDrawerSheet(
            modifier = Modifier.fillMaxWidth(0.75f)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxHeight()
            ) {
                Spacer(Modifier.height(12.dp))
                CreateText(
                    "Settings",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider()

                NavigationDrawerItem(
                    label = { CreateText("Dark Mode") },
                    selected = false,
                    icon = {
                        CreateIcon(
                            Icons.Filled.DarkMode,
                            "Change theme"
                        )
                    },
                    onClick = {
                        println("Changing theme...")
                        handleThemeChange()
                        onDrawerClose()
                    }
                )

                NavigationDrawerItem(
                    label = { CreateText("Language") },
                    selected = false,
                    icon = {
                        CreateIcon(
                            Icons.Filled.Language,
                            "Change language"
                        )
                    },
                    onClick = {
                        println("Changing language...")
                        handleLanguageChange()
                        onDrawerClose()
                    }
                )

                // Extra space to accommodate language options (Spanish/English)
                Spacer(Modifier.weight(1f))
            }
        }
    }

    /**
     * Logic to change theme
     */
    private fun handleThemeChange() {
        // TODO: Implement theme change logic
        println("Theme changed")
    }

    /**
     * Logic to change language
     */
    private fun handleLanguageChange() {
        // TODO: Implement language change logic
        println("Language changed")
    }
}
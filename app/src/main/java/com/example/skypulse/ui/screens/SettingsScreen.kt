package com.example.skypulse.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.skypulse.components.common.CreateDropdown
import com.example.skypulse.components.common.CreateIcon
import com.example.skypulse.components.common.CreateText
import com.example.skypulse.components.common.rememberDropdownState
import com.example.skypulse.enums.ThemeMode
import com.example.skypulse.managers.ThemeManager
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
                DrawerContent()
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
    private fun DrawerContent() {
        val themeDropdownState = rememberDropdownState()
        val languageDropdownState = rememberDropdownState()
        val themeIcon = remember { mutableStateOf(ThemeManager.getIconTheme()) }

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
                    label = { CreateText("Change theme") },
                    selected = false,
                    icon = {
                        CreateIcon(
                            themeIcon.value,
                            "Change theme"
                        )
                    },
                    onClick = {
                        themeDropdownState.toggle()
                        languageDropdownState.closeIfNecessary()
                    }
                )

                CreateDropdown(themeDropdownState) {
                    DropdownMenuItem(
                        text = { CreateText("Light") },
                        onClick = {
                            ThemeManager.setThemeMode(ThemeMode.LIGHT)
                            themeIcon.value = ThemeManager.getIconTheme()
                            themeDropdownState.toggle()
                        }
                    )

                    DropdownMenuItem(
                        text = { CreateText("Dark") },
                        onClick = {
                            ThemeManager.setThemeMode(ThemeMode.DARK)
                            themeIcon.value = ThemeManager.getIconTheme()
                            themeDropdownState.toggle()
                        }
                    )

                    DropdownMenuItem(
                        text = { CreateText("Synchronise with OS") },
                        onClick = {
                            ThemeManager.setThemeMode(ThemeMode.SYSTEM)
                            themeIcon.value = ThemeManager.getIconTheme()
                            themeDropdownState.toggle()
                        }
                    )
                }

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
                        languageDropdownState.toggle()
                        themeDropdownState.closeIfNecessary()
                    }
                )

                CreateDropdown(languageDropdownState) {
                    DropdownMenuItem(
                        text = { CreateText("Spanish") },
                        onClick = {
                            println("Changing the app language to spanish")
                        }
                    )

                    DropdownMenuItem(
                        text = { CreateText("English") },
                        onClick = {
                            println("Changing the app language to english")
                        }
                    )
                }

                // Extra space to accommodate language options (Spanish/English)
                Spacer(Modifier.weight(1f))
            }
        }
    }
}
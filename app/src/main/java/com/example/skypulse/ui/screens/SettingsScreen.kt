package com.example.skypulse.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.DrawerState
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object SettingsScreen {
    private var drawerState: DrawerState? = null
    private var scope: CoroutineScope? = null

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun createUI() {
        drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState!!,
            drawerContent = {
                ModalDrawerSheet {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Spacer(Modifier.height(12.dp))
                        CreateText(
                            "Drawer Title",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.titleLarge
                        )
                        HorizontalDivider()
                        NavigationDrawerItem(
                            label = { CreateText("") },
                            selected = false,
                            icon = {
                                CreateIcon(
                                    Icons.Filled.DarkMode,
                                    "Change theme"
                                )
                            },
                            onClick = {
                                println("Changing theme...")
                            }
                        )
                        NavigationDrawerItem(
                            label = { CreateText("") },
                            selected = false,
                            icon = {
                                CreateIcon(
                                    Icons.Filled.Language,
                                    "Change language"
                                )
                            },
                            onClick = {
                                println("Changing language...")
                            }
                        )
                    }
                }
            }
        ) {}
    }

    fun toggleDrawerState() {
        println("Toggling drawer state")
        scope?.launch {
            println("Scope")
            drawerState?.apply {
                println("Changing drawer state")
                if (isClosed) open() else close()
                println("Drawer state: ${drawerState!!.currentValue}")
            }
        }
    }
}
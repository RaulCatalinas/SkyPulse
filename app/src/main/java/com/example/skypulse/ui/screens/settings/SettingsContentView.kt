package com.example.skypulse.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.skypulse.R
import com.example.skypulse.components.common.CreateDropdown
import com.example.skypulse.components.common.CreateIcon
import com.example.skypulse.components.common.CreateText
import com.example.skypulse.components.common.rememberDropdownState
import com.example.skypulse.enums.ThemeMode
import com.example.skypulse.managers.ThemeManager

/**
 * Settings drawer content
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsContentView() {
    val themeDropdownState = rememberDropdownState()
    val languageDropdownState = rememberDropdownState()
    val themeIcon = remember {
        mutableStateOf(ThemeManager.getIconTheme())
    }

    // Load string resources
    val changeThemeText = stringResource(R.string.change_theme)
    val settingsText = stringResource(R.string.settings)
    val lightThemeText = stringResource(R.string.light_theme)
    val darkThemeText = stringResource(R.string.dark_theme)
    val systemDefaultThemeText = stringResource(R.string.system_default_theme)

    ModalDrawerSheet(modifier = Modifier.fillMaxWidth(0.75f)) {
        Column(
            modifier =
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxHeight()
        ) {
            Spacer(Modifier.height(12.dp))
            CreateText(
                text = settingsText,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )

            HorizontalDivider()

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                NavigationDrawerItem(
                    label = { CreateText(changeThemeText) },
                    selected = false,
                    icon = {
                        CreateIcon(
                            themeIcon.value,
                            changeThemeText
                        )
                    },
                    onClick = {
                        languageDropdownState.close()
                        themeDropdownState.toggle()
                    }
                )

                CreateDropdown(themeDropdownState) {
                    DropdownMenuItem(
                        text = { CreateText(lightThemeText) },
                        onClick = {
                            ThemeManager.setThemeMode(ThemeMode.LIGHT)
                            themeIcon.value = ThemeManager.getIconTheme()
                            themeDropdownState.toggle()
                        }
                    )

                    DropdownMenuItem(
                        text = { CreateText(darkThemeText) },
                        onClick = {
                            ThemeManager.setThemeMode(ThemeMode.DARK)
                            themeIcon.value = ThemeManager.getIconTheme()
                            themeDropdownState.toggle()
                        }
                    )

                    DropdownMenuItem(
                        text = { CreateText(systemDefaultThemeText) },
                        onClick = {
                            ThemeManager.setThemeMode(ThemeMode.SYSTEM)
                            themeIcon.value = ThemeManager.getIconTheme()
                            themeDropdownState.toggle()
                        }
                    )
                }
            }

            // Extra space to accommodate language options (Spanish/English)
            Spacer(Modifier.weight(1f))
        }
    }
}
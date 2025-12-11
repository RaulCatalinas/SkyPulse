package com.example.skypulse.components.common

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

/**
 * Composable function to remember dropdown state
 * Usage: val dropdownState = rememberDropdownState()
 */
@Composable
fun rememberDropdownState(initialExpanded: Boolean = false): DropdownState {
    return remember { DropdownState(initialExpanded) }
}

/**
 * State holder for dropdown menu
 * Manages expanded/collapsed state
 */
class DropdownState(initialExpanded: Boolean = false) {
    var expanded by mutableStateOf(initialExpanded)
        private set

    /**
     * Close the dropdown
     */
    fun closeIfNecessary() {
        if (expanded) return

        expanded = false
    }

    /**
     * Toggle dropdown state
     */
    fun toggle() {
        expanded = !expanded
    }
}

/**
 * Dropdown menu with internal state management
 * Can be controlled from outside using DropdownState
 *
 * @param state The dropdown state (use rememberDropdownState())
 * @param modifier Modifier for the dropdown menu
 * @param content The dropdown menu items
 */
@Composable
fun CreateDropdown(
    state: DropdownState,
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.() -> Unit)
) {
    DropdownMenu(
        expanded = state.expanded,
        onDismissRequest = { },
        modifier = modifier,
        content = content
    )
}
package com.example.sonora.data

import android.content.Context
import com.example.sonora.ui.theme.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemePreferences(context: Context) {

    private val prefs = context.applicationContext
        .getSharedPreferences("sonora_prefs", Context.MODE_PRIVATE)

    private val _mode = MutableStateFlow(readMode())
    val mode: StateFlow<ThemeMode> = _mode.asStateFlow()

    private fun readMode(): ThemeMode {
        val raw = prefs.getString(KEY_MODE, ThemeMode.System.name) ?: ThemeMode.System.name
        return runCatching { ThemeMode.valueOf(raw) }.getOrDefault(ThemeMode.System)
    }

    fun setMode(mode: ThemeMode) {
        _mode.value = mode
        prefs.edit().putString(KEY_MODE, mode.name).apply()
    }

    fun toggle() {
        setMode(
            when (_mode.value) {
                ThemeMode.System -> ThemeMode.Light
                ThemeMode.Light -> ThemeMode.Dark
                ThemeMode.Dark -> ThemeMode.System
            }
        )
    }

    companion object {
        private const val KEY_MODE = "theme_mode"
    }
}
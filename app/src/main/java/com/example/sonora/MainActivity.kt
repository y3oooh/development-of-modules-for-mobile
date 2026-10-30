package com.example.sonora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.example.sonora.data.ThemePreferences
import com.example.sonora.ui.navigation.AppNav
import com.example.sonora.ui.theme.SonoraTheme
import com.example.sonora.ui.theme.ThemeMode

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val themePreferences = ThemePreferences(this)

        setContent {
            val mode by themePreferences.mode.collectAsState()
            val darkTheme = when (mode) {
                ThemeMode.System -> isSystemInDarkTheme()
                ThemeMode.Light -> false
                ThemeMode.Dark -> true
            }
            SonoraTheme(darkTheme = darkTheme) {
                val windowSize = calculateWindowSizeClass(this)
                AppNav(
                    windowSize = windowSize,
                    themeMode = mode,
                    onToggleTheme = themePreferences::toggle,
                )
            }
        }
    }
}
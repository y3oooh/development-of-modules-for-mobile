package com.example.sonora.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sonora.ui.theme.SonoraTheme

@Composable
fun SectionHeader(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
    )
}

@Preview(name = "Section — светлая")
@Composable
private fun SectionHeaderLightPreview() {
    SonoraTheme(darkTheme = false) { Surface { SectionHeader("Плейлист") } }
}

@Preview(name = "Section — тёмная", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SectionHeaderDarkPreview() {
    SonoraTheme(darkTheme = true) { Surface { SectionHeader("Плейлист") } }
}
package com.example.sonora.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sonora.ui.theme.SonoraTheme

@Composable
fun TrackProgressBar(
    progress: Float,
    positionLabel: String,
    durationLabel: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        LinearProgressIndicator(
            progress = { progress.coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            drawStopIndicator = {},
            gapSize = 0.dp,
        )
        Spacer(Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = positionLabel,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontFeatureSettings = "tnum"   // tabular numbers
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = durationLabel,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontFeatureSettings = "tnum"
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Preview(name = "Progress — светлая")
@Composable
private fun TrackProgressBarLightPreview() {
    SonoraTheme(darkTheme = false) {
        Surface {
            TrackProgressBar(
                progress = 0.32f,
                positionLabel = "1:02",
                durationLabel = "3:34",
                modifier = Modifier.padding(24.dp),
            )
        }
    }
}

@Preview(name = "Progress — тёмная", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TrackProgressBarDarkPreview() {
    SonoraTheme(darkTheme = true) {
        Surface {
            TrackProgressBar(
                progress = 0.32f,
                positionLabel = "1:02",
                durationLabel = "3:34",
                modifier = Modifier.padding(24.dp),
            )
        }
    }
}
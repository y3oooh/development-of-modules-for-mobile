package com.example.sonora.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sonora.R
import com.example.sonora.data.model.Track
import com.example.sonora.ui.theme.SonoraTheme

@Composable
fun TrackRow(
    track: Track,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val highlight = track.isPlaying
    val bg = if (highlight) MaterialTheme.colorScheme.primaryContainer
    else MaterialTheme.colorScheme.surface

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CoverArt(
            coverRes = track.coverRes,
            modifier = Modifier.size(48.dp),
        )
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = track.title,
                style = MaterialTheme.typography.titleMedium,
                color = if (highlight) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
            )
            Text(
                text = if (highlight) stringResource(R.string.now_playing) else track.artist,
                style = MaterialTheme.typography.bodyMedium,
                color = if (highlight) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
            )
        }
        Spacer(Modifier.width(12.dp))
        if (highlight) {
            Icon(
                imageVector = Icons.Filled.GraphicEq,
                contentDescription = stringResource(R.string.now_playing),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp),
            )
            Spacer(Modifier.width(8.dp))
        }
        Text(
            text = formatDuration(track.durationSec),
            style = MaterialTheme.typography.labelLarge.copy(fontFeatureSettings = "tnum"),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

private fun formatDuration(totalSec: Int): String {
    val m = totalSec / 60
    val s = totalSec % 60
    return "%d:%02d".format(m, s)
}

@Preview(name = "Track — обычный, светлая")
@Composable
private fun TrackRowNormalLightPreview() {
    SonoraTheme(darkTheme = false) {
        Surface {
            TrackRow(
                track = Track("1", "Paper Planes", "Luna Park", 187),
                onClick = {},
                modifier = Modifier.padding(12.dp),
            )
        }
    }
}

@Preview(name = "Track — играет, тёмная", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TrackRowPlayingDarkPreview() {
    SonoraTheme(darkTheme = true) {
        Surface {
            TrackRow(
                track = Track("1", "Midnight Drive", "Neon Waves", 214, isPlaying = true),
                onClick = {},
                modifier = Modifier.padding(12.dp),
            )
        }
    }
}
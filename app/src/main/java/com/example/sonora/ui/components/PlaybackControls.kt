package com.example.sonora.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sonora.R
import com.example.sonora.ui.theme.SonoraTheme

@Composable
fun PlaybackControls(
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = onPrevious,
            modifier = Modifier.size(56.dp),
        ) {
            Icon(
                imageVector = Icons.Filled.SkipPrevious,
                contentDescription = stringResource(R.string.player_previous),
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(36.dp),
            )
        }

        FilledIconButton(
            onClick = onPlayPause,
            modifier = Modifier.size(72.dp),
        ) {
            Icon(
                imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                contentDescription = stringResource(
                    if (isPlaying) R.string.player_pause else R.string.player_play
                ),
                modifier = Modifier.size(40.dp),
            )
        }

        IconButton(
            onClick = onNext,
            modifier = Modifier.size(56.dp),
        ) {
            Icon(
                imageVector = Icons.Filled.SkipNext,
                contentDescription = stringResource(R.string.player_next),
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(36.dp),
            )
        }
    }
}

@Preview(name = "Controls — играет, светлая")
@Composable
private fun PlaybackControlsPlayingLightPreview() {
    SonoraTheme(darkTheme = false) {
        Surface {
            PlaybackControls(
                isPlaying = true,
                onPlayPause = {}, onNext = {}, onPrevious = {},
                modifier = Modifier.padding(24.dp),
            )
        }
    }
}

@Preview(name = "Controls — пауза, тёмная", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PlaybackControlsPausedDarkPreview() {
    SonoraTheme(darkTheme = true) {
        Surface {
            PlaybackControls(
                isPlaying = false,
                onPlayPause = {}, onNext = {}, onPrevious = {},
                modifier = Modifier.padding(24.dp),
            )
        }
    }
}
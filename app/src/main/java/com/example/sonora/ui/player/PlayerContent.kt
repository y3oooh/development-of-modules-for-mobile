package com.example.sonora.ui.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sonora.data.FakeRepository
import com.example.sonora.data.model.PlayerState
import com.example.sonora.ui.components.CoverArt
import com.example.sonora.ui.components.PlaybackControls
import com.example.sonora.ui.components.TrackProgressBar
import com.example.sonora.ui.theme.SonoraTheme
import com.example.sonora.util.WidthBucket
import com.example.sonora.util.horizontalPadding

@Composable
fun PlayerContent(
    player: PlayerState,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    widthBucket: WidthBucket = WidthBucket.Compact,
    modifier: Modifier = Modifier,
) {
    val padding = widthBucket.horizontalPadding()

    if (widthBucket == WidthBucket.Compact) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = padding, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CoverArt(coverRes = player.currentTrack.coverRes)
            Spacer(Modifier.height(24.dp))
            TrackInfo(player)
            Spacer(Modifier.height(24.dp))
            TrackProgressBar(
                progress = player.progress,
                positionLabel = formatDuration(player.positionSec),
                durationLabel = formatDuration(player.currentTrack.durationSec),
            )
            Spacer(Modifier.height(24.dp))
            PlaybackControls(
                isPlaying = player.isPlaying,
                onPlayPause = onPlayPause,
                onNext = onNext,
                onPrevious = onPrevious,
            )
        }
    } else {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = padding, vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(modifier = Modifier.weight(0.45f)) {
                CoverArt(coverRes = player.currentTrack.coverRes)
            }
            Spacer(Modifier.width(32.dp))
            Column(
                modifier = Modifier.weight(0.55f),
                horizontalAlignment = Alignment.Start,
            ) {
                TrackInfo(player)
                Spacer(Modifier.height(24.dp))
                TrackProgressBar(
                    progress = player.progress,
                    positionLabel = formatDuration(player.positionSec),
                    durationLabel = formatDuration(player.currentTrack.durationSec),
                )
                Spacer(Modifier.height(24.dp))
                PlaybackControls(
                    isPlaying = player.isPlaying,
                    onPlayPause = onPlayPause,
                    onNext = onNext,
                    onPrevious = onPrevious,
                )
            }
        }
    }
}

@Composable
private fun TrackInfo(player: PlayerState) {
    Text(
        text = player.currentTrack.title,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onSurface,
    )
    Spacer(Modifier.height(4.dp))
    Text(
        text = player.currentTrack.artist,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

private fun formatDuration(totalSec: Int): String {
    val m = totalSec / 60
    val s = totalSec % 60
    return "%d:%02d".format(m, s)
}

@Preview(name = "Player Content — светлая")
@Composable
private fun PlayerContentLightPreview() {
    SonoraTheme(darkTheme = false) {
        Surface {
            PlayerContent(
                player = FakeRepository.samplePlayerState(),
                onPlayPause = {}, onNext = {}, onPrevious = {},
            )
        }
    }
}

@Preview(name = "Player Content — тёмная", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PlayerContentDarkPreview() {
    SonoraTheme(darkTheme = true) {
        Surface {
            PlayerContent(
                player = FakeRepository.samplePlayerState(),
                onPlayPause = {}, onNext = {}, onPrevious = {},
            )
        }
    }
}

@Preview(name = "Player Content — Expanded", widthDp = 1000)
@Composable
private fun PlayerContentExpandedPreview() {
    SonoraTheme(darkTheme = false) {
        Surface {
            PlayerContent(
                player = FakeRepository.samplePlayerState(),
                onPlayPause = {}, onNext = {}, onPrevious = {},
                widthBucket = WidthBucket.Expanded,
            )
        }
    }
}
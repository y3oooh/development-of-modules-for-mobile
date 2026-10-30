package com.example.sonora.ui.playlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sonora.data.FakeRepository
import com.example.sonora.data.model.Playlist
import com.example.sonora.data.model.Track
import com.example.sonora.ui.components.SectionHeader
import com.example.sonora.ui.components.TrackRow
import com.example.sonora.ui.theme.SonoraTheme
import com.example.sonora.util.WidthBucket
import com.example.sonora.util.horizontalPadding

@Composable
fun PlaylistContent(
    playlist: Playlist,
    selectedTrackId: String?,
    onTrackClick: (Track) -> Unit,
    widthBucket: WidthBucket = WidthBucket.Compact,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        SectionHeader(
            text = playlist.name,
            modifier = Modifier.padding(
                horizontal = widthBucket.horizontalPadding(),
                vertical = 8.dp,
            ),
        )
        when (widthBucket) {
            WidthBucket.Compact, WidthBucket.Medium -> LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = widthBucket.horizontalPadding(),
                    vertical = 8.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(playlist.tracks, key = { it.id }) { track ->
                    TrackRow(
                        track = track.copy(isPlaying = track.id == selectedTrackId),
                        onClick = { onTrackClick(track) },
                    )
                }
            }
            WidthBucket.Expanded -> LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = widthBucket.horizontalPadding(),
                    vertical = 8.dp,
                ),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(playlist.tracks, key = { it.id }) { track ->
                    TrackRow(
                        track = track.copy(isPlaying = track.id == selectedTrackId),
                        onClick = { onTrackClick(track) },
                    )
                }
            }
        }
    }
}

@Preview(name = "Playlist — светлая")
@Composable
private fun PlaylistContentLightPreview() {
    SonoraTheme(darkTheme = false) {
        Surface {
            PlaylistContent(
                playlist = FakeRepository.samplePlaylist(),
                selectedTrackId = "2",
                onTrackClick = {},
            )
        }
    }
}

@Preview(name = "Playlist — тёмная", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PlaylistContentDarkPreview() {
    SonoraTheme(darkTheme = true) {
        Surface {
            PlaylistContent(
                playlist = FakeRepository.samplePlaylist(),
                selectedTrackId = "2",
                onTrackClick = {},
            )
        }
    }
}

@Preview(name = "Playlist — Expanded", widthDp = 1000)
@Composable
private fun PlaylistContentExpandedPreview() {
    SonoraTheme(darkTheme = false) {
        Surface {
            PlaylistContent(
                playlist = FakeRepository.samplePlaylist(),
                selectedTrackId = "2",
                onTrackClick = {},
                widthBucket = WidthBucket.Expanded,
            )
        }
    }
}
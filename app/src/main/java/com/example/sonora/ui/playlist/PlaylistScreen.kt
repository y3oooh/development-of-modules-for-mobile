package com.example.sonora.ui.playlist

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sonora.data.FakeRepository
import com.example.sonora.data.model.Track
import com.example.sonora.ui.components.EmptyState
import com.example.sonora.ui.components.SkeletonCard
import com.example.sonora.ui.theme.SonoraTheme
import com.example.sonora.util.WidthBucket
import com.example.sonora.util.widthBucket

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun PlaylistScreen(
    windowSize: WindowSizeClass,
    viewModel: PlaylistViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    val state by viewModel.uiState.collectAsState()
    val selectedTrackId by viewModel.selectedTrackId.collectAsState()
    PlaylistScreenContent(
        state = state,
        selectedTrackId = selectedTrackId,
        widthBucket = windowSize.widthBucket(),
        onTrackClick = viewModel::onTrackClick,
        onRetry = viewModel::load,
        modifier = modifier,
    )
}

@Composable
fun PlaylistScreenContent(
    state: PlaylistUiState,
    selectedTrackId: String?,
    widthBucket: WidthBucket = WidthBucket.Compact,
    onTrackClick: (Track) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        AnimatedContent(
            targetState = state,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "playlist_state",
        ) { s ->
            when (s) {
                PlaylistUiState.Loading -> Box(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    SkeletonCard(modifier = Modifier.fillMaxWidth())
                }
                PlaylistUiState.Empty -> EmptyState(onAction = onRetry)
                is PlaylistUiState.Content -> PlaylistContent(
                    playlist = s.playlist,
                    selectedTrackId = selectedTrackId,
                    widthBucket = widthBucket,
                    onTrackClick = onTrackClick,
                )
            }
        }
    }
}

@Preview(name = "Playlist Loading — светлая", showBackground = true)
@Composable
private fun PlaylistLoadingLightPreview() {
    SonoraTheme(darkTheme = false) {
        PlaylistScreenContent(PlaylistUiState.Loading, null, onTrackClick = {}, onRetry = {})
    }
}

@Preview(name = "Playlist Loading — тёмная", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun PlaylistLoadingDarkPreview() {
    SonoraTheme(darkTheme = true) {
        PlaylistScreenContent(PlaylistUiState.Loading, null, onTrackClick = {}, onRetry = {})
    }
}

@Preview(name = "Playlist Empty — светлая", showBackground = true)
@Composable
private fun PlaylistEmptyLightPreview() {
    SonoraTheme(darkTheme = false) {
        PlaylistScreenContent(PlaylistUiState.Empty, null, onTrackClick = {}, onRetry = {})
    }
}

@Preview(name = "Playlist Empty — тёмная", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun PlaylistEmptyDarkPreview() {
    SonoraTheme(darkTheme = true) {
        PlaylistScreenContent(PlaylistUiState.Empty, null, onTrackClick = {}, onRetry = {})
    }
}

@Preview(name = "Playlist Content — светлая", showBackground = true)
@Composable
private fun PlaylistContentLightPreview() {
    SonoraTheme(darkTheme = false) {
        PlaylistScreenContent(
            state = PlaylistUiState.Content(FakeRepository.samplePlaylist()),
            selectedTrackId = "2",
            onTrackClick = {}, onRetry = {},
        )
    }
}

@Preview(name = "Playlist Content — тёмная", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun PlaylistContentDarkPreview() {
    SonoraTheme(darkTheme = true) {
        PlaylistScreenContent(
            state = PlaylistUiState.Content(FakeRepository.samplePlaylist()),
            selectedTrackId = "2",
            onTrackClick = {}, onRetry = {},
        )
    }
}

@Preview(name = "Playlist Content — Expanded", widthDp = 1000, showBackground = true)
@Composable
private fun PlaylistContentExpandedPreview() {
    SonoraTheme(darkTheme = false) {
        PlaylistScreenContent(
            state = PlaylistUiState.Content(FakeRepository.samplePlaylist()),
            selectedTrackId = "2",
            widthBucket = WidthBucket.Expanded,
            onTrackClick = {}, onRetry = {},
        )
    }
}
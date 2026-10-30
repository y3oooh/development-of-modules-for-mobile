package com.example.sonora.ui.player

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sonora.data.FakeRepository
import com.example.sonora.ui.components.EmptyState
import com.example.sonora.ui.components.SkeletonCard
import com.example.sonora.ui.theme.SonoraTheme
import com.example.sonora.util.WidthBucket
import com.example.sonora.util.widthBucket

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun PlayerScreen(
    windowSize: WindowSizeClass,
    viewModel: PlayerViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    val state by viewModel.uiState.collectAsState()
    PlayerScreenContent(
        state = state,
        widthBucket = windowSize.widthBucket(),
        onPlayPause = viewModel::onPlayPause,
        onNext = viewModel::onNext,
        onPrevious = viewModel::onPrevious,
        onRetry = viewModel::onRetry,
        modifier = modifier,
    )
}

@Composable
fun PlayerScreenContent(
    state: PlayerUiState,
    widthBucket: WidthBucket = WidthBucket.Compact,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
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
            label = "player_state",
        ) { s ->
            when (s) {
                PlayerUiState.Loading -> Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    SkeletonCard(modifier = Modifier.fillMaxWidth())
                }
                PlayerUiState.Empty -> EmptyState(onAction = onRetry)
                is PlayerUiState.Content -> PlayerContent(
                    player = s.player,
                    onPlayPause = onPlayPause,
                    onNext = onNext,
                    onPrevious = onPrevious,
                    widthBucket = widthBucket,
                )
            }
        }
    }
}

@Preview(name = "Player Loading — светлая", showBackground = true)
@Composable
private fun PlayerLoadingLightPreview() {
    SonoraTheme(darkTheme = false) {
        PlayerScreenContent(
            state = PlayerUiState.Loading,
            onPlayPause = {}, onNext = {}, onPrevious = {}, onRetry = {},
        )
    }
}

@Preview(name = "Player Loading — тёмная", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun PlayerLoadingDarkPreview() {
    SonoraTheme(darkTheme = true) {
        PlayerScreenContent(
            state = PlayerUiState.Loading,
            onPlayPause = {}, onNext = {}, onPrevious = {}, onRetry = {},
        )
    }
}

@Preview(name = "Player Empty — светлая", showBackground = true)
@Composable
private fun PlayerEmptyLightPreview() {
    SonoraTheme(darkTheme = false) {
        PlayerScreenContent(
            state = PlayerUiState.Empty,
            onPlayPause = {}, onNext = {}, onPrevious = {}, onRetry = {},
        )
    }
}

@Preview(name = "Player Empty — тёмная", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun PlayerEmptyDarkPreview() {
    SonoraTheme(darkTheme = true) {
        PlayerScreenContent(
            state = PlayerUiState.Empty,
            onPlayPause = {}, onNext = {}, onPrevious = {}, onRetry = {},
        )
    }
}

@Preview(name = "Player Content — светлая", showBackground = true)
@Composable
private fun PlayerContentLightPreview() {
    SonoraTheme(darkTheme = false) {
        PlayerScreenContent(
            state = PlayerUiState.Content(FakeRepository.samplePlayerState()),
            onPlayPause = {}, onNext = {}, onPrevious = {}, onRetry = {},
        )
    }
}

@Preview(name = "Player Content — тёмная", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun PlayerContentDarkPreview() {
    SonoraTheme(darkTheme = true) {
        PlayerScreenContent(
            state = PlayerUiState.Content(FakeRepository.samplePlayerState()),
            onPlayPause = {}, onNext = {}, onPrevious = {}, onRetry = {},
        )
    }
}

@Preview(name = "Player Content — Expanded", widthDp = 1000, showBackground = true)
@Composable
private fun PlayerContentExpandedPreview() {
    SonoraTheme(darkTheme = false) {
        PlayerScreenContent(
            state = PlayerUiState.Content(FakeRepository.samplePlayerState()),
            widthBucket = WidthBucket.Expanded,
            onPlayPause = {}, onNext = {}, onPrevious = {}, onRetry = {},
        )
    }
}
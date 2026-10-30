package com.example.sonora.ui.playlist

import com.example.sonora.data.model.Playlist

sealed interface PlaylistUiState {
    data object Loading : PlaylistUiState
    data object Empty : PlaylistUiState
    data class Content(val playlist: Playlist) : PlaylistUiState
}
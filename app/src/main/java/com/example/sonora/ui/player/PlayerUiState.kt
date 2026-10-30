package com.example.sonora.ui.player

import com.example.sonora.data.model.PlayerState

sealed interface PlayerUiState {
    data object Loading : PlayerUiState
    data object Empty : PlayerUiState
    data class Content(val player: PlayerState) : PlayerUiState
}
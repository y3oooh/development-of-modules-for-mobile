package com.example.sonora.ui.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonora.data.FakeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlayerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<PlayerUiState>(PlayerUiState.Loading)
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    init {
        load(empty = false)
    }

    fun load(empty: Boolean = false) {
        _uiState.value = PlayerUiState.Loading
        viewModelScope.launch {
            val result = FakeRepository.loadPlayerState(empty = empty)
            _uiState.value = if (result == null) PlayerUiState.Empty
            else PlayerUiState.Content(result)
        }
    }

    fun onPlayPause() {
        val current = _uiState.value
        if (current is PlayerUiState.Content) {
            _uiState.value = PlayerUiState.Content(
                current.player.copy(isPlaying = !current.player.isPlaying)
            )
        }
    }

    fun onNext() {
        val current = _uiState.value
        if (current is PlayerUiState.Content) {
            val queue = current.player.queue
            if (queue.isEmpty()) return
            val idx = queue.indexOfFirst { it.id == current.player.currentTrack.id }
            val next = queue[(idx + 1).coerceAtLeast(0) % queue.size]
            _uiState.value = PlayerUiState.Content(
                current.player.copy(currentTrack = next, positionSec = 0)
            )
        }
    }

    fun onPrevious() {
        val current = _uiState.value
        if (current is PlayerUiState.Content) {
            val queue = current.player.queue
            if (queue.isEmpty()) return
            val idx = queue.indexOfFirst { it.id == current.player.currentTrack.id }
            val prev = queue[(idx - 1 + queue.size) % queue.size]
            _uiState.value = PlayerUiState.Content(
                current.player.copy(currentTrack = prev, positionSec = 0)
            )
        }
    }

    fun onRetry() = load(empty = false)
}
package com.example.sonora.ui.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonora.data.FakeRepository
import com.example.sonora.data.model.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlaylistViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<PlaylistUiState>(PlaylistUiState.Loading)
    val uiState: StateFlow<PlaylistUiState> = _uiState.asStateFlow()

    private val _selectedTrackId = MutableStateFlow<String?>(null)
    val selectedTrackId: StateFlow<String?> = _selectedTrackId.asStateFlow()

    init {
        load()
    }

    fun load() {
        _uiState.value = PlaylistUiState.Loading
        viewModelScope.launch {
            val result = FakeRepository.loadPlaylist()
            _uiState.value = if (result == null || result.tracks.isEmpty())
                PlaylistUiState.Empty
            else
                PlaylistUiState.Content(result)
        }
    }

    fun onTrackClick(track: Track) {
        _selectedTrackId.value = track.id
    }
}
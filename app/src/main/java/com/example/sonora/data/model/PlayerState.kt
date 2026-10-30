package com.example.sonora.data.model

data class PlayerState(
    val currentTrack: Track,
    val positionSec: Int,
    val isPlaying: Boolean,
    val queue: List<Track>,
) {
    val progress: Float
        get() = if (currentTrack.durationSec > 0)
            positionSec.toFloat() / currentTrack.durationSec
        else 0f
}
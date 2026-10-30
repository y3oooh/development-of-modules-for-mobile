package com.example.sonora.data.model

data class Track(
    val id: String,
    val title: String,
    val artist: String,
    val durationSec: Int,
    val coverRes: Int? = null,
    val isPlaying: Boolean = false,
)
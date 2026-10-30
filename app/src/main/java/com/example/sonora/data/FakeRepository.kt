package com.example.sonora.data

import com.example.sonora.data.model.Playlist
import com.example.sonora.data.model.PlayerState
import com.example.sonora.data.model.Track
import kotlinx.coroutines.delay

object FakeRepository {

    private val sampleTracks = listOf(
        Track("1", "Midnight Drive", "Neon Waves", 214, isPlaying = true),
        Track("2", "Paper Planes", "Luna Park", 187),
        Track("3", "Velvet Sky", "The Analog Hearts", 243),
        Track("4", "Golden Hour", "Mira Sol", 201),
        Track("5", "Static Bloom", "Neon Waves", 176),
    )

    suspend fun loadPlayerState(empty: Boolean = false): PlayerState? {
        delay(5000)
        if (empty) return null
        val current = sampleTracks.first()
        return PlayerState(
            currentTrack = current,
            positionSec = 62,
            isPlaying = true,
            queue = sampleTracks,
        )
    }

    suspend fun loadPlaylist(empty: Boolean = false): Playlist? {
        delay(1500)
        if (empty) return null
        return Playlist(id = "p1", name = "Late Night Mix", tracks = sampleTracks)
    }

    fun samplePlayerState(): PlayerState = PlayerState(
        currentTrack = sampleTracks.first(),
        positionSec = 62,
        isPlaying = true,
        queue = sampleTracks,
    )

    fun samplePlaylist(): Playlist = Playlist(id = "p1", name = "Late Night Mix", tracks = sampleTracks)
}
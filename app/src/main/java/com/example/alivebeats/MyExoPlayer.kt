package com.example.alivebeats

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.alivebeats.modelo.Song
import com.google.firebase.firestore.FirebaseFirestore

object MyExoPlayer {
    private var exoPlayer: ExoPlayer? = null
    private var currentSong: Song? = null

    fun getCurrentSong(): Song?{
    return  currentSong
    }
    fun getInstace(): ExoPlayer?{
        return exoPlayer
    }
    fun starPlaying(context : Context, song: Song){
        if (exoPlayer == null)
            exoPlayer = ExoPlayer.Builder(context).build()

        // Verifica si la canción actual es diferente de la que se va a reproducir
        if (currentSong != song) {
            // Asigna la nueva canción como la canción actual y actualiza el contador de reproducciones
            currentSong = song
            updateCount()

            // Obtiene la URL de la canción actual y configura el reproductor con ella
            currentSong?.url?.apply {
                val mediaItem = MediaItem.fromUri(this)
                exoPlayer?.setMediaItem(mediaItem)
                exoPlayer?.prepare()
                exoPlayer?.play()
            }

        }
    }

    fun updateCount() {
        // Verifica si hay una canción actual seleccionada
        currentSong?.id?.let { id ->
            // Accede a la colección "songs" en Firestore y obtiene el documento correspondiente al ID de la canción
            FirebaseFirestore.getInstance().collection("songs")
                .document(id)
                .get().addOnSuccessListener { documentSnapshot ->
                    // Obtiene el contador actual de reproducciones de la canción
                    var latestCount = documentSnapshot.getLong("count") ?: 1L
                    // Incrementa el contador
                    latestCount++
                    // Actualiza el contador en la base de datos de Firestore
                    FirebaseFirestore.getInstance().collection("songs")
                        .document(id)
                        .update(mapOf("count" to latestCount))
                }
        }
    }

}
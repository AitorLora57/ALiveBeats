package com.example.alivebeats

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.alivebeats.modelo.Song

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
        if(exoPlayer==null)
            exoPlayer=ExoPlayer.Builder(context).build()

        if(currentSong!=song){
            currentSong= song


            currentSong?.url?.apply {

                val mediaItem = MediaItem.fromUri(this)
                exoPlayer?.setMediaItem(mediaItem)
                exoPlayer?.prepare()
                exoPlayer?.play()


        }

        }
    }
}
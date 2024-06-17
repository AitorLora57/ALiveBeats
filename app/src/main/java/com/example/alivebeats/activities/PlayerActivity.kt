package com.example.alivebeats.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.Glide
import com.example.alivebeats.MyExoPlayer
import com.example.alivebeats.R
import com.example.alivebeats.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerBinding
    private lateinit var exoPlayer: ExoPlayer
    private var playerListener = object  : Player.Listener{
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            showGif(isPlaying)
        }

    }

 override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        MyExoPlayer.getCurrentSong()?.apply {
            binding.songTitleTextView.text=title
            binding.songArtistTextView.text=artist
            Glide.with(binding.songCoverImageView).load(coverUrl)
                .circleCrop()
                .into(binding.songCoverImageView)
            Glide.with(binding.songGifImageView).load(R.drawable.media_playing)
                .circleCrop()
                .into(binding.songGifImageView)

            exoPlayer = MyExoPlayer.getInstace()!!
            binding.playerView.player=exoPlayer
            binding.playerView.showController()
            exoPlayer.addListener(playerListener)
        }
    }

    override fun onDestroy() {
        // Llama al método onDestroy de la superclase
        super.onDestroy()

        // Remueve el listener del ExoPlayer, si existe
        exoPlayer?.removeListener(playerListener)
    }

    fun showGif(show: Boolean) {
        // Verifica si se debe mostrar el GIF o no
        if (show) {
            // Si show es true, establece la visibilidad del ImageView como VISIBLE
            binding.songGifImageView.visibility = View.VISIBLE
        } else {
            // Si show es false, establece la visibilidad del ImageView como INVISIBLE
            binding.songGifImageView.visibility = View.INVISIBLE
        }
    }


}
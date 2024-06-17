package com.example.alivebeats

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.alivebeats.adaptador.SongsListAlbumAdapter
import com.example.alivebeats.databinding.ActivitySongAlbumListBinding
import com.example.alivebeats.databinding.ActivitySongsListBinding
import com.example.alivebeats.modelo.Album

class SongAlbumList : AppCompatActivity() {

    companion object{
        lateinit var album:Album
    }
    private lateinit var  binding: ActivitySongAlbumListBinding
    private  lateinit var albumListAlbumAdapter: SongsListAlbumAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySongAlbumListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.nameAlbumTextView.text= album.name
        Glide.with(binding.coverImageView).load(album.url)
            .apply(
                RequestOptions().transform(RoundedCorners(32)))
            .into(binding.coverImageView)
        setRecylcerSong()
    }


    fun setRecylcerSong(){

        albumListAlbumAdapter = SongsListAlbumAdapter(album.songs)
        binding.songAlbumListRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.songAlbumListRecyclerView.adapter= albumListAlbumAdapter

    }
}
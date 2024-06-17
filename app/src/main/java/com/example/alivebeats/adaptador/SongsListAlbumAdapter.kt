package com.example.alivebeats.adaptador

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.alivebeats.MyExoPlayer
import com.example.alivebeats.activities.PlayerActivity
import com.example.alivebeats.databinding.SongAlbumListRecyclerRowBinding
import com.example.alivebeats.modelo.Song
import com.google.firebase.firestore.FirebaseFirestore

class SongsListAlbumAdapter (private val albumList:List<String>):
    RecyclerView.Adapter<SongsListAlbumAdapter.MyViewHolder>(){

    class MyViewHolder(private val binding: SongAlbumListRecyclerRowBinding) : RecyclerView.ViewHolder
        (binding.root){
        fun bindData(songId: String){
            FirebaseFirestore.getInstance().collection("songs").document(songId).get()
                .addOnSuccessListener {

                    val song = it.toObject(Song::class.java)
                    song?.apply {
                        binding.tituloCancionAlbum.text = title
                        binding.artistaCancionAlbum.text = artist
                        Glide.with(binding.songAlbumCoverImageView).load(coverUrl)
                            .apply(
                                RequestOptions().transform(RoundedCorners(32))
                            )
                            .into(binding.songAlbumCoverImageView)
                        binding.root.setOnClickListener {
                            MyExoPlayer.starPlaying(binding.root.context,song)
                            it.context.startActivity(Intent(it.context, PlayerActivity::class.java))
                        }
                    }
                }
        }

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(albumList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding= SongAlbumListRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

}
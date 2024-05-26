package com.example.alivebeats.adaptador

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.alivebeats.MyExoPlayer
import com.example.alivebeats.PlayerActivity
import com.example.alivebeats.databinding.ListaCancionesRecyclerRowBinding
import com.example.alivebeats.modelo.Song
import com.google.firebase.firestore.FirebaseFirestore

class SongsListAdapter(private val songIdList: List<String>):
    RecyclerView.Adapter<SongsListAdapter.MyViewHolder>() {
    class MyViewHolder(private val binding: ListaCancionesRecyclerRowBinding) : RecyclerView.ViewHolder
        (binding.root){
            fun bindData(songId: String){
                FirebaseFirestore.getInstance().collection("songs").document(songId).get()
                    .addOnSuccessListener {

                        val song = it.toObject(Song::class.java)
                        song?.apply {
                            binding.tituloCancion.text = title
                            binding.artistaCancion.text = artist
                            Glide.with(binding.songCoverImageView).load(coverUrl)
                                .apply(
                                    RequestOptions().transform(RoundedCorners(32))
                                )
                                .into(binding.songCoverImageView)
                            binding.root.setOnClickListener {
                                MyExoPlayer.starPlaying(binding.root.context,song)
                                it.context.startActivity(Intent(it.context,PlayerActivity::class.java))
                            }
                        }
                    }
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val binding = ListaCancionesRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongsListAdapter.MyViewHolder, position: Int) {
        holder.bindData(songIdList[position])
    }

    override fun getItemCount(): Int {
      return  songIdList.size
    }


}
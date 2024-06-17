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
import com.example.alivebeats.databinding.SectionSongListRecyclerRowBinding
import com.example.alivebeats.modelo.Song
import com.google.firebase.firestore.FirebaseFirestore

class SectionSongListAdapter(private val songIdList: List<String>):
    RecyclerView.Adapter<SectionSongListAdapter.MyViewHolder>() {
    class MyViewHolder(private val binding: SectionSongListRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
        // Método para asignar datos a las vistas del ViewHolder
        fun bindData(songId: String) {
            // Accede a la colección "songs" en Firestore y obtiene el documento correspondiente al ID de la canción
            FirebaseFirestore.getInstance().collection("songs").document(songId).get()
                .addOnSuccessListener { documentSnapshot ->
                    // Convierte el documento en un objeto de la clase Song
                    val song = documentSnapshot.toObject(Song::class.java)
                    song?.apply {
                        // Asigna el título y el artista de la canción a los TextView correspondientes
                        binding.songTitleTextView.text = title
                        binding.songSubtitleTextView.text = artist
                        // Carga la imagen de la portada de la canción utilizando Glide y la muestra en el ImageView

                        Glide.with(binding.songCoverImageView)
                            .load(coverUrl)
                            .apply(RequestOptions().transform(RoundedCorners(32))) // Aplica bordes redondeados a la imagen
                            .into(binding.songCoverImageView)


                        binding.root.setOnClickListener {
                            MyExoPlayer.starPlaying(binding.root.context, song) // Inicia la reproducción de la canción

                            it.context.startActivity(Intent(it.context, PlayerActivity::class.java))
                        }
                    }
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = SectionSongListRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SectionSongListAdapter.MyViewHolder, position: Int) {
        holder.bindData(songIdList[position])
    }

    override fun getItemCount(): Int {
        return  songIdList.size
    }


}
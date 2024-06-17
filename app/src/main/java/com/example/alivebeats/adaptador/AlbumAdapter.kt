package com.example.alivebeats.adaptador
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.alivebeats.SongAlbumList
import com.example.alivebeats.databinding.AlbumItemRecyclerRowBinding
import com.example.alivebeats.modelo.Album


class AlbumAdapter(private val albumList: List<Album>) :
    RecyclerView.Adapter<AlbumAdapter.MyViewHolder>(){
    class MyViewHolder(private val binding: AlbumItemRecyclerRowBinding)
        : RecyclerView.ViewHolder(binding.root){
           fun bindData(album : Album){
           binding.albumNameTextView.text = album.name
              binding.artistTextView.text= album.artist
               Glide.with(binding.coverImageView).load(album.url)
                   .apply(
                       RequestOptions().transform(RoundedCorners(32)))
                   .into(binding.coverImageView)
               val context = binding.root.context
               binding.root.setOnClickListener{
                   SongAlbumList.album = album
                    context.startActivity(Intent(context,SongAlbumList::class.java))
               }
           }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val binding = AlbumItemRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return  albumList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
     holder.bindData(albumList[position])
    }
}



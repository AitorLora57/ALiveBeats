package com.example.alivebeats
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.alivebeats.adaptador.SongsListAdapter
import com.example.alivebeats.databinding.ActivitySongsListBinding
import com.example.alivebeats.modelo.Category

class SongsListActivity : AppCompatActivity() {

    companion object{
        lateinit var category: Category
    }
    private lateinit var  binding: ActivitySongsListBinding
    private lateinit var  songsListAdapter: SongsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding= ActivitySongsListBinding.inflate(layoutInflater)
            setContentView(binding.root)
         binding.nameTextView.text= category.name
             Glide.with(binding.coverImageView).load(category.url)
                .apply(
                    RequestOptions().transform(RoundedCorners(32)))
                .into(binding.coverImageView)
        setRecyclerView()
    }

    private fun setRecyclerView(){

        songsListAdapter = SongsListAdapter(category.songs)
        binding.songListRecyclerView.layoutManager=LinearLayoutManager(this)
        binding.songListRecyclerView.adapter = songsListAdapter
    }
}
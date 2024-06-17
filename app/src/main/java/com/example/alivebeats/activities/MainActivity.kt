package com.example.alivebeats.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.alivebeats.MyExoPlayer
import com.example.alivebeats.R
import com.example.alivebeats.adaptador.AlbumAdapter
import com.example.alivebeats.adaptador.CategoryAdapter
import com.example.alivebeats.adaptador.SectionSongListAdapter
import com.example.alivebeats.databinding.ActivityMainBinding
import com.example.alivebeats.modelo.Album
import com.example.alivebeats.modelo.Category
import com.example.alivebeats.modelo.Song
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObjects

class MainActivity : AppCompatActivity() {
lateinit var binding: ActivityMainBinding
private lateinit var categoryAdapter: CategoryAdapter
private lateinit var albumAdapter: AlbumAdapter
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getCategories()
        getAlbums()
        setSection("section_1",binding.section1MainLayaout,binding.section1Title,binding.section1RecyclerView)
        setSection("section_2",binding.section2MainLayaout,binding.section2Title,binding.section2RecyclerView)
        setUpMostly("mas_escuchadas",binding.section3MainLayaout,binding.section3Title,binding.section3RecyclerView)
        binding.btnOption.setOnClickListener {
            showPopMenu()
        }

    }

    private fun showPopMenu() {
        val popUpMenu = PopupMenu(this,binding.btnOption)
        val inflator = popUpMenu.menuInflater
        inflator.inflate(R.menu.option_menu,popUpMenu.menu)
        popUpMenu.show()
        popUpMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.cerrar_session ->{
                    logout()

                }
                R.id.Buscar ->{
                    startActivity(Intent(this, SongsResult::class.java))

                }
            }
            false
        }
    }

    private fun logout() {
        MyExoPlayer.getInstace()?.release()
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }


    override fun onResume() {
    super.onResume()
    showPlayerView()
    }
    @SuppressLint("SetTextI18n")
    fun showPlayerView(){
    binding.playerView.setOnClickListener{
        startActivity(Intent(this, PlayerActivity::class.java))
    }
    MyExoPlayer.getCurrentSong()?.let {
        binding.playerView.visibility = View.VISIBLE
        binding.songTitleView.text = "Escuchando : " + it.title
        Glide.with(binding.songCoverImageView).load(it.coverUrl)
            .apply(
                RequestOptions().transform(RoundedCorners(32))
            )  .into(binding.songCoverImageView)
    }?: run{
        binding.playerView.visibility = View.GONE
    }
}

    //Categorias
    private fun getCategories(){
        FirebaseFirestore.getInstance().collection("categoria")
            .get().addOnSuccessListener {
                val categoryList = it.toObjects(Category::class.java)
                setCategoryRecyclerView(categoryList)
        }
    }

    private fun setCategoryRecyclerView(categorylist : List<Category>){
        categoryAdapter = CategoryAdapter(categorylist)
        binding.categoriesRecyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.categoriesRecyclerView.adapter=categoryAdapter
    }

    //Album
    private fun getAlbums(){
        FirebaseFirestore.getInstance().collection("albums")
            .get().addOnSuccessListener {
                val albumList = it.toObjects(Album::class.java)
                setAlbumRecyclerView(albumList)
        }
    }

    private fun setAlbumRecyclerView(albumList : List<Album>){
        albumAdapter = AlbumAdapter(albumList)
        binding.albumRecyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.albumRecyclerView.adapter=albumAdapter
    }

    //Secciones
    private fun setSection(id : String , mainLayout : RelativeLayout , titleView: TextView,recyclerView: RecyclerView){

        FirebaseFirestore.getInstance().collection("sections")
            .document(id)
            .get().addOnSuccessListener {
                val section = it.toObject(Category::class.java)
                section?.apply {
                   mainLayout.visibility= View.VISIBLE
                    titleView.text=name
                    recyclerView.layoutManager=LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
                    recyclerView.adapter=SectionSongListAdapter(songs)
                    mainLayout.setOnClickListener {
                        SongsListActivity.category = section
                        startActivity(Intent(this@MainActivity, SongsListActivity::class.java))
                    }
                }
            }
    }
   private fun setUpMostly(id : String, mainLayout : RelativeLayout, titleView: TextView, recyclerView: RecyclerView){

        FirebaseFirestore.getInstance().collection("sections")
        .document(id)
        .get().addOnSuccessListener {
            //get most played songs

            FirebaseFirestore.getInstance().collection("songs")
            .orderBy("count", Query.Direction.DESCENDING)
            .limit(10)
            .get().addOnSuccessListener {songListSnapshot->

                val songList=  songListSnapshot.toObjects<Song>()

                val songsIdList = songList.map {
                    it.id
               }.toList()

                val section = it.toObject(Category::class.java)
                section?.apply {
                    section.songs=songsIdList
                    mainLayout.visibility= View.VISIBLE
                    titleView.text=name
                    recyclerView.layoutManager=LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
                    recyclerView.adapter=SectionSongListAdapter(songs)
                    mainLayout.setOnClickListener {
                        SongsListActivity.category = section
                        startActivity(Intent(this@MainActivity, SongsListActivity::class.java))
                    }
                }
            }
        }
   }
}
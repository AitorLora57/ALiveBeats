package com.example.alivebeats

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.alivebeats.adaptador.CategoryAdapter
import com.example.alivebeats.adaptador.SectionSongListAdapter
import com.example.alivebeats.databinding.ActivityMainBinding
import com.example.alivebeats.modelo.Category
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
lateinit var binding: ActivityMainBinding
lateinit var categoryAdapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getCategories()
        setSection("section_1",binding.section1MainLayaout,binding.section1Title,binding.section1RecyclerView)
        setSection("section_2",binding.section2MainLayaout,binding.section2Title,binding.section2RecyclerView)
    }

    override fun onResume() {
        super.onResume()
        showPlayerView()
    }
fun showPlayerView(){
    binding.playerView.setOnClickListener{
        startActivity(Intent(this,PlayerActivity::class.java))

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
    fun getCategories(){
        FirebaseFirestore.getInstance().collection("categoria")
            .get().addOnSuccessListener {
                val categoryList = it.toObjects(Category::class.java)
                setCategoryRecyclerView(categoryList)
        }
    }

    fun setCategoryRecyclerView(categorylist : List<Category>){
        categoryAdapter = CategoryAdapter(categorylist)
        binding.categoriesRecyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.categoriesRecyclerView.adapter=categoryAdapter

    }

    //Secciones
    fun setSection(id : String , mainLayout : RelativeLayout , titleView: TextView,recyclerView: RecyclerView){

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
                        startActivity(Intent(this@MainActivity,SongsListActivity::class.java))

                    }


                }


            }



    }

}
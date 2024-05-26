package com.example.alivebeats

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alivebeats.adaptador.CategoryAdapter
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
    }

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
}
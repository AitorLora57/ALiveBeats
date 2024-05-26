package com.example.alivebeats.adaptador
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.alivebeats.SongsListActivity
import com.example.alivebeats.databinding.CategoriaItemRecyclerRowBinding
import com.example.alivebeats.modelo.Category

class CategoryAdapter(private val categoryList: List<Category>) : RecyclerView.Adapter<CategoryAdapter.MyViewHolder>(){

    class MyViewHolder(private val binding: CategoriaItemRecyclerRowBinding)
        : RecyclerView.ViewHolder(binding.root){


        fun bindData(category: Category){
            binding.nameTextView.text = category.name
            Glide.with(binding.coverImageView).load(category.url)
                .apply(
                    RequestOptions().transform(RoundedCorners(32)))
                .into(binding.coverImageView)
                val context = binding.root.context
            Log.i("SONGS" , category.songs.size.toString())
            binding.root.setOnClickListener{
                SongsListActivity.category = category
                context.startActivity(Intent(context,SongsListActivity::class.java))
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val binding = CategoriaItemRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
     return categoryList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.bindData(categoryList[position])
    }
}
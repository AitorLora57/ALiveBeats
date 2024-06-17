package com.example.alivebeats.adaptador
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.alivebeats.activities.SongsListActivity
import com.example.alivebeats.databinding.CategoriaItemRecyclerRowBinding
import com.example.alivebeats.modelo.Category

class CategoryAdapter(private val categoryList: List<Category>) : RecyclerView.Adapter<CategoryAdapter.MyViewHolder>(){

    class MyViewHolder(private val binding: CategoriaItemRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
        // Método para asignar datos de categoría a las vistas del ViewHolder
        fun bindData(category: Category) {
            // Asigna el nombre de la categoría al TextView correspondiente
            binding.nameTextView.text = category.name
            // Carga la imagen de la categoría utilizando Glide y la muestra en el ImageView
            Glide.with(binding.coverImageView)
                .load(category.url)
                // Aplica bordes redondeados a la imagen
                .apply(RequestOptions().transform(RoundedCorners(32)))
                .into(binding.coverImageView)

            val context = binding.root.context //

            binding.root.setOnClickListener {
                SongsListActivity.category = category //
                context.startActivity(Intent(context, SongsListActivity::class.java))
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
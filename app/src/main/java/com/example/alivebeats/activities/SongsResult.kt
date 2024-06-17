package com.example.alivebeats.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alivebeats.R
import com.example.alivebeats.adaptador.SongsListResultAdapter
import com.example.alivebeats.databinding.ActivitySongsResultBinding
import com.example.alivebeats.modelo.Song
import com.google.firebase.firestore.FirebaseFirestore

class SongsResult : AppCompatActivity() {
    lateinit var binding: ActivitySongsResultBinding
    private lateinit var songsListResultAdapter: SongsListResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongsResultBinding.inflate(layoutInflater)
        Log.i("PRUEBAS", "probando")
        setContentView(binding.root)

        binding.btnBuscar.setOnClickListener {
            val nameSong: String = binding.editBsucar.text.toString()

            if (nameSong.isEmpty()) {
                Toast.makeText(this, "Ingrese el nombre de la cancion", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                // Obtener todas las canciones
                FirebaseFirestore.getInstance().collection("songs").get().addOnSuccessListener { documents ->
                    val allSongs = documents.toObjects(Song::class.java)
                    // Filtrar las canciones que contienen el nombre buscado, ignorando mayúsculas/minúsculas
                    val resultList = allSongs.filter { it.title.contains(nameSong, ignoreCase = true) }
                    songsListResultAdapter = SongsListResultAdapter(resultList)

                    binding.songListResultRecyclerView.layoutManager =
                        LinearLayoutManager(
                            binding.root.context,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                    binding.songListResultRecyclerView.adapter = songsListResultAdapter
                }
            }
        }
    }
}

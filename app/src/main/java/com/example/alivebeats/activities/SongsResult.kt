package com.example.alivebeats.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alivebeats.R
import com.example.alivebeats.adaptador.CategoryAdapter
import com.example.alivebeats.adaptador.SongsListAdapter
import com.example.alivebeats.adaptador.SongsListResultAdapter
import com.example.alivebeats.databinding.ActivitySongsResultBinding
import com.example.alivebeats.modelo.Category
import com.example.alivebeats.modelo.Song
import com.google.firebase.firestore.FirebaseFirestore

class SongsResult : AppCompatActivity() {
    lateinit var binding: ActivitySongsResultBinding
    private lateinit var songsListResultAdapter: SongsListResultAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("PRUEBAS" , "probando")
        setContentView(R.layout.activity_songs_result)



        binding.btnBuscar.setOnClickListener {

            val nameSong: String = binding.editBsucar.text.toString()

            if (nameSong.isEmpty()) {
                Log.i("Prueba", "Probando")
            } else {

                // Canciones
                FirebaseFirestore.getInstance().collection("songs")
                    .whereEqualTo("title", nameSong).get().addOnSuccessListener { documents ->
                        val resultList = documents.toObjects(Song::class.java)
                        songsListResultAdapter = SongsListResultAdapter(resultList)

                        binding.songListResultRecyclerView.layoutManager =
                            LinearLayoutManager(
                                binding.root.context,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        binding.songListResultRecyclerView.adapter = songsListResultAdapter
                    }

            }

        }
    }

}
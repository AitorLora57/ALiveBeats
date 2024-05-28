package com.example.alivebeats

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.alivebeats.databinding.ActivitySingUpBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class SingUpActivity : AppCompatActivity() {


    lateinit var binding: ActivitySingUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnConfimar.setOnClickListener {

            val  email= binding.emailTextEdit.text.toString()
            val  password= binding.passwordTextEdit.text.toString()
            val  confirmPassword= binding.confirmPasswordTextEdit.text.toString()

            if(!Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(),email)){
                binding.emailTextEdit.setError("Email no válido")
                return@setOnClickListener
            }
            if (password.length <6){
                binding.passwordTextEdit.setError("Mínimo 6 caracteres")
                return@setOnClickListener

            }
            if (!password.equals(confirmPassword)){
                binding.confirmPasswordTextEdit.setError("La contraseña no coincide")
                return@setOnClickListener
            }
            createAccount(email,password)
        }

        binding.gotoLogin.setOnClickListener{

            finish()
        }
    }

    private fun createAccount(email: String, password: String) {
        setInprogress(true)
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                setInprogress(false)
                Toast.makeText(applicationContext,"Usuario creado correctamente" , Toast.LENGTH_SHORT).show()
                finish()

            }.addOnFailureListener {
                setInprogress(false)
               Toast.makeText(applicationContext,"Error al crear la cuenta" , Toast.LENGTH_SHORT).show()
            }
    }

    private fun setInprogress(inProgress :Boolean){
        if(inProgress){
            binding.btnConfimar.visibility=View.GONE
            binding.progressBar.visibility=View.VISIBLE

        }else{
            binding.btnConfimar.visibility=View.VISIBLE
            binding.progressBar.visibility=View.GONE

        }


    }
}

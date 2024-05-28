package com.example.alivebeats

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.alivebeats.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import org.checkerframework.common.returnsreceiver.qual.This
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIniciar.setOnClickListener {
            val  email= binding.emailTextEdit.text.toString()
            val  password= binding.passwordTextEdit.text.toString()

            if(!Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(),email)){
                binding.emailTextEdit.setError("Email no válido")
                return@setOnClickListener
            }
            if (password.length <6){
                binding.passwordTextEdit.setError("Mínimo 6 caracteres")
                return@setOnClickListener
            }
            loginAccount(email, password)
        }

        binding.gotoSignup.setOnClickListener{
            startActivity(Intent(this,SingUpActivity::class.java))
        }
    }

    private fun loginAccount(email: String, password: String) {
        setInprogress(true)
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                setInprogress(false)
            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                finish()

            }.addOnFailureListener {
                setInprogress(false)
                Toast.makeText(applicationContext,"Error al iniciar sesión" , Toast.LENGTH_SHORT).show()
            }
    }

    override fun onResume() {
        super.onResume()
        FirebaseAuth.getInstance().currentUser?.apply {
            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
            finish()
        }
    }
    private fun setInprogress(inProgress :Boolean){
        if(inProgress){
            binding.btnIniciar.visibility= View.GONE
            binding.progressBar.visibility= View.VISIBLE

        }else{
            binding.btnIniciar.visibility= View.VISIBLE
            binding.progressBar.visibility= View.GONE
        }


    }
}

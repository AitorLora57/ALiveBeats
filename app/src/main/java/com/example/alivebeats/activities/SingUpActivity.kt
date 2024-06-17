package com.example.alivebeats.activities

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.alivebeats.databinding.ActivitySingUpBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class SingUpActivity : AppCompatActivity() {


    lateinit var binding: ActivitySingUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Infla el diseño de la actividad de registro usando View Binding
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura el botón de confirmar para manejar el registro del usuario
        binding.btnConfimar.setOnClickListener {
            // Obtiene el correo electrónico, la contraseña y la confirmación de la contraseña de los campos de texto
            val email = binding.emailTextEdit.text.toString()
            val password = binding.passwordTextEdit.text.toString()
            val confirmPassword = binding.confirmPasswordTextEdit.text.toString()

            // Verifica que el correo electrónico tenga un formato válido
            if (!Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(), email)) {
                binding.emailTextEdit.setError("Email no válido")
                return@setOnClickListener
            }

            // Verifica que la contraseña tenga al menos 6 caracteres
            if (password.length < 6) {
                binding.passwordTextEdit.setError("Mínimo 6 caracteres")
                return@setOnClickListener
            }

            // Verifica que la contraseña y la confirmación de la contraseña coincidan
            if (!password.equals(confirmPassword)) {
                binding.confirmPasswordTextEdit.setError("La contraseña no coincide")
                return@setOnClickListener
            }

            // Llama a la función para crear una cuenta con el correo electrónico y la contraseña proporcionados
            createAccount(email, password)
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

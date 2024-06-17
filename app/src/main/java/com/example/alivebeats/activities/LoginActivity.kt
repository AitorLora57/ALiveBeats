package com.example.alivebeats.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.alivebeats.R
import com.example.alivebeats.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIniciar.setOnClickListener {
            // Obtiene el correo electrónico y la contraseña de los campos de texto
            val  email= binding.emailTextEdit.text.toString()
            val  password= binding.passwordTextEdit.text.toString()
            // Verifica que la contraseña tenga al menos 6 caracteres
            if(!Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(),email)){
                binding.emailTextEdit.setError(getString(R.string.email_no_v_lido))
                return@setOnClickListener
            }
            if (password.length <6){
                binding.passwordTextEdit.setError(getString(R.string.m_nimo_6_caracteres))
                return@setOnClickListener
            }
            // Llama a la función para iniciar sesión con el correo electrónico y la contraseña
            loginAccount(email, password)
        }
        // Configura el botón para ir a la actividad de registro (sign up)
        binding.gotoSignup.setOnClickListener{
            startActivity(Intent(this, SingUpActivity::class.java))
        }
    }

    private fun loginAccount(email: String, password: String) {
        // Activa el estado de progreso (por ejemplo, muestra un indicador de carga)
        setInprogress(true)

        // Llama al método de Firebase Authentication para iniciar sesión con correo electrónico y contraseña
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // Si el inicio de sesión es exitoso, desactiva el estado de progreso
                setInprogress(false)

                // Navega a la actividad principal de la aplicación
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))

                // Finaliza la actividad de inicio de sesión para que no esté en el back stack
                finish()
            }
            .addOnFailureListener {
                // Si el inicio de sesión falla, desactiva el estado de progreso
                setInprogress(false)

                // Muestra un mensaje de error al usuario
                Toast.makeText(applicationContext,
                    getString(R.string.correo_o_contrase_a_incorrecta), Toast.LENGTH_SHORT).show()
            }
    }


    override fun onResume() {
        super.onResume()

        // Verifica si hay un usuario actualmente autenticado
        FirebaseAuth.getInstance().currentUser?.apply {
            // Si hay un usuario autenticado, inicia la actividad principal y finaliza la actividad de inicio de sesión
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
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

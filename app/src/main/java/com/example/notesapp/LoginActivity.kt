package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.example.notesapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        login()
        binding.registerButton.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.txtPassword.setOnKeyListener{ _, _, _ ->
            if(isPasswordValid(binding.txtPassword.text!!)){
                binding.LayoutPassword.error = null
            }
            false
        }
    }

    fun login(){
        binding.loginButton.setOnClickListener{
            when {
                binding.txtEmail.text.toString().isEmpty() -> {
                    binding.LayoutEmail.error = getString(R.string.error_email)
                }
                binding.txtPassword.text.toString().isEmpty() -> {
                    binding.LayoutPassword.error = getString(R.string.error_password)
                }
                else -> {
                    FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(
                            binding.txtEmail.text.toString(),
                            binding.txtPassword.text.toString()
                        )
                        .addOnSuccessListener (this) {
                            val home: Intent = Intent(this, MainActivity::class.java)
                            startActivity(home)
                        }
                        .addOnFailureListener(this){
                            Toast.makeText(applicationContext,"Error: ${it.message.toString()}",Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
    private fun isPasswordValid(text:Editable?) : Boolean{
        return text != null && text.length >=6
    }
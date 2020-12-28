package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.notesapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val db:FirebaseFirestore = FirebaseFirestore.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        register()
    }

    fun register(){
        binding.registerButton.setOnClickListener {
            when{
                binding.firstNameEditText.text.toString().isEmpty() ->{
                    binding.firstNameTextInput.error = getString(R.string.error_first_name)
                }
                binding.lastNameEditText.text.toString().isEmpty() ->{
                    binding.lastNameTextInput.error = getString(R.string.error_last_name)
                }
                binding.emailEditText.text.toString().isEmpty() ->{
                    binding.emailTextInput.error = getString(R.string.error_email)
                }
                binding.passwordEditText.text.toString().isEmpty() ->{
                    binding.passwordTextInput.error = getString(R.string.error_password)
                }
                else -> {
                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(
                            binding.emailEditText.text.toString(),
                            binding.passwordEditText.text.toString()
                        ).addOnSuccessListener {
                            onAuth(it.user!!)
                            Toast.makeText(applicationContext,"Usuario creado con exito!!!", Toast.LENGTH_LONG).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(applicationContext,"Error al crear el usuario ${it.message.toString()}", Toast.LENGTH_LONG).show()
                        }
                }
            }
        }
    }
    fun onAuth(user:FirebaseUser){
        createNewUser(user.uid)
    }
    fun createNewUser(uid:String){
        val user:User = buildNewUser()
        db.collection("users").document(uid).set(user)
            .addOnSuccessListener { Log.d("TAG","Dcoumento escrito exitosamente!!!") }
            .addOnFailureListener { e -> Log.w("TAG", "Error al escribir el documento",e) }
    }
    fun buildNewUser():User{
        return User(
            binding.firstNameEditText.text.toString(),
            binding.lastNameEditText.text.toString(),
            binding.emailEditText.text.toString(),
            Date().time
        )
    }
}
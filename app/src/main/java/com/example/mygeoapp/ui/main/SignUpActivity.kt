package com.example.mygeoapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.example.mygeoapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.backButton.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.signUpButton.setOnClickListener{

            val name = binding.nameEditText.text.toString()
            val email = binding.emailSignUpEditText.text.toString()
            val pass = binding.passwordSignUpEditText.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty() && name.isNotEmpty()){
                firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                    if (it.isSuccessful){
                        val intent = Intent(this, SignInActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this, it.exception.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(this, "Empty Fields Are Not Allowed!",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
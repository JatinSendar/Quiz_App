package com.example.quizzed.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.quizzed.R
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        firebaseAuth = FirebaseAuth.getInstance()
        val btnSignup = findViewById<Button>(R.id.btnSignUp)
        val tvLogin = findViewById<TextView>(R.id.tvLogin)

        btnSignup.setOnClickListener { signUpUser() }
        tvLogin.setOnClickListener{
            val intent=Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun signUpUser() {

        val email = findViewById<EditText>(R.id.etEmail).text.toString()
        val password = findViewById<EditText>(R.id.etPassword).text.toString()
        val cmPassword = findViewById<EditText>(R.id.etCmPassword).text.toString()


        if (email.isBlank() || password.isBlank() || cmPassword.isBlank()){
            Toast.makeText(this,"Email and Password can't be blank",Toast.LENGTH_SHORT).show()
            return

        }
        if (password != cmPassword){
            Toast.makeText(this,"Password and Confirm Password Do not match",Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){
            if (it.isSuccessful){
                Toast.makeText(this,"New User Created Successfully",Toast.LENGTH_SHORT).show()
                val intent=Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"Error Creating User",Toast.LENGTH_SHORT).show()
            }
        }
    }


}
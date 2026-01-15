package com.example.verv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.verv.data.MoodRepository

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        MoodRepository.initialize(this)

        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val tvRegister = findViewById<TextView>(R.id.tv_register)

        btnLogin.setOnClickListener {
            val inputEmail = etEmail.text.toString().trim()
            val inputPassword = etPassword.text.toString().trim()
            
            if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in email and password", Toast.LENGTH_SHORT).show()
            } else {
                val registeredUser = UserRepository.getUser(this)
                if (registeredUser != null && inputEmail == registeredUser.email && inputPassword == registeredUser.password) {
                    
                    UserRepository.saveUser(this, registeredUser)
                    
                    Toast.makeText(this, "Welcome back, ${registeredUser.name}!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("USER_NAME", registeredUser.name)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Invalid Email or Password!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
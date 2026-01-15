package com.example.verv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegister = findViewById<Button>(R.id.btn_register)
        val tvLogin = findViewById<TextView>(R.id.tv_login)

        val etName = findViewById<EditText>(R.id.et_reg_name)
        val etEmail = findViewById<EditText>(R.id.et_reg_email)
        val etPhone = findViewById<EditText>(R.id.et_reg_phone)     // Baru
        val etAddress = findViewById<EditText>(R.id.et_reg_address) // Baru
        val etPassword = findViewById<EditText>(R.id.et_reg_password)

        btnRegister.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val phone = etPhone.text.toString()
            val address = etAddress.text.toString()
            val password = etPassword.text.toString()
              if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && phone.isNotEmpty() && address.isNotEmpty()) {

                val newUser = User(
                    name = name,
                    email = email,
                    password = password,
                    phone = phone,
                    location = address
                )
                UserRepository.saveUser(this, newUser)

                Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        tvLogin.setOnClickListener {
            finish()
        }
    }
}
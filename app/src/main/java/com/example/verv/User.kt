package com.example.verv

data class User(
    val name: String,
    val email: String,
    val location: String = "Yogyakarta",
    val password: String,
    val phone: String = "0812345678"
)

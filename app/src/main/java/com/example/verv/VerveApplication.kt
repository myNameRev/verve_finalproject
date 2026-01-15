package com.example.verv

import android.app.Application
import com.example.verv.data.MoodRepository

class VerveApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MoodRepository.initialize(this)
    }
}
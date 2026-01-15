package com.example.verv

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.verv.data.MoodNote
import com.example.verv.data.MoodRepository
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddMoodActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MoodRepository.initialize(this)

        val sharedPreferences = getSharedPreferences("AppSetting", MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("NightMode", false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        setContentView(R.layout.activity_add_mood)

        val btnTheme = findViewById<ImageView?>(R.id.btn_theme_toggle)
        btnTheme?.setOnClickListener {
            val currentMode = sharedPreferences.getBoolean("NightMode", false)
            val editor = sharedPreferences.edit()

            if (currentMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("NightMode", false)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("NightMode", true)
            }
            editor.apply()
        }

        findViewById<TextView>(R.id.tv_header_text).text = "Add Mood"
        val tilTitle = findViewById<TextInputLayout>(R.id.til_note_title)
        val etTitle = findViewById<TextInputEditText>(R.id.et_note_title)
        val btnSave = findViewById<Button>(R.id.btn_save_note)

        val selectedDateFromCalendar = intent.getStringExtra("SELECTED_DATE")

        btnSave.setOnClickListener {
            val titleInput = etTitle.text.toString().trim()

            if (titleInput.isEmpty()) {
                tilTitle.error = "Please tell me how you feel"
            } else {
                tilTitle.error = null
                val selectedEmoji = MoodRepository.getEmojiFromTitle(titleInput)
                val moodDate = selectedDateFromCalendar ?: SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(Date())

                val newMood = MoodNote(
                    title = titleInput,
                    emoji = selectedEmoji,
                    date = moodDate,
                    isFavorite = false
                )
                
                MoodRepository.addMood(newMood)

                Toast.makeText(this, "Mood Saved Successfully!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
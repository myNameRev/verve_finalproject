package com.example.verv

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("AppSetting", MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("NightMode", false)

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        setContentView(R.layout.activity_main)
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
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val fabAdd = findViewById<FloatingActionButton>(R.id.fab_add)
        val tvHeader = findViewById<TextView>(R.id.tv_header_text)

        loadFragment(HomeFragment())
        bottomNav.selectedItemId = R.id.nav_notes
        tvHeader.text = "Mood List"

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_calendar -> {
                    tvHeader.text = "Calendar"
                    loadFragment(CalendarFragment())
                    true
                }
                R.id.nav_profile -> {
                    tvHeader.text = "My Profile"
                    loadFragment(ProfileFragment())
                    true
                }
                R.id.nav_notes -> {
                    tvHeader.text = "Mood List"
                    loadFragment(HomeFragment())
                    true
                }
                R.id.nav_favorites -> {
                    tvHeader.text = "Favorites"
                    loadFragment(FavoritesFragment())
                    true
                }
                else -> false
            }
        }

        fabAdd.setOnClickListener {
            val intent = Intent(this, AddMoodActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
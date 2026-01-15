package com.example.verv.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MoodNote::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moodNoteDao(): MoodNoteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mood_database"
                )
                .fallbackToDestructiveMigration() // Reset otomatis jika versi database berubah (Mencegah Crash)
                .allowMainThreadQueries() 
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
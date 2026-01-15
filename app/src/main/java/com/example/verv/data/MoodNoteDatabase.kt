package com.example.verv.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MoodNote::class], version = 1, exportSchema = false)
abstract class MoodNoteDatabase : RoomDatabase() {

    abstract fun moodNoteDao(): MoodNoteDao

    companion object {
        @Volatile
        private var INSTANCE: MoodNoteDatabase? = null

        fun getDatabase(context: Context): MoodNoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoodNoteDatabase::class.java,
                    "mood_note_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

package com.example.verv.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MoodNoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: MoodNote)

    @Update
    fun update(note: MoodNote)

    @Delete
    fun delete(note: MoodNote)

    @Query("SELECT * FROM mood_notes ORDER BY id DESC")
    fun getAllNotes(): List<MoodNote>

    @Query("SELECT * FROM mood_notes WHERE isFavorite = 1")
    fun getFavoriteNotes(): List<MoodNote>
}
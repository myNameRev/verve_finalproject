package com.example.verv.data

import android.content.Context

object MoodRepository {
    private var dao: MoodNoteDao? = null

    fun initialize(context: Context) {
        if (dao == null) {
            dao = AppDatabase.getDatabase(context).moodNoteDao()
        }
    }

    private fun getDao(): MoodNoteDao {
        return dao ?: throw IllegalStateException("MoodRepository must be initialized first")
    }

    fun getAllMoods(): List<MoodNote> {
        return try {
            getDao().getAllNotes()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getFavorites(): List<MoodNote> {
        return try {
            getDao().getFavoriteNotes()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun deleteMood(mood: MoodNote) {
        try {
            getDao().delete(mood)
        } catch (e: Exception) { }
    }

    fun updateMood(mood: MoodNote) {
        try {
            getDao().update(mood)
        } catch (e: Exception) { }
    }

    fun addMood(mood: MoodNote) {
        try {
            getDao().insert(mood)
        } catch (e: Exception) { }
    }

    fun getEmojiFromTitle(title: String): String {
        val lower = title.lowercase()
        return when {
            // Sangat Senang / Excited
            lower.contains("semangat") || lower.contains("excited") || lower.contains("kagum") || 
            lower.contains("mantap") || lower.contains("hore") || lower.contains("wow") -> "🤩"
            
            // Senang / Happy
            lower.contains("senang") || lower.contains("happy") || lower.contains("bahagia") || 
            lower.contains("gembira") || lower.contains("ceria") || lower.contains("smile") || 
            lower.contains("tertawa") || lower.contains("lucu") -> "😄"
            
            // Sedih / Sad
            lower.contains("sedih") || lower.contains("nangis") || lower.contains("galau") || 
            lower.contains("sad") || lower.contains("cry") || lower.contains("kecewa") || 
            lower.contains("patah hati") || lower.contains("bad mood") -> "😭"
            
            // Marah / Angry
            lower.contains("marah") || lower.contains("benci") || lower.contains("kesal") || 
            lower.contains("angry") || lower.contains("ngamuk") || lower.contains("emosi") -> "😡"
            
            // Cinta / Love
            lower.contains("cinta") || lower.contains("sayang") || lower.contains("love") || 
            lower.contains("suka") || lower.contains("kangen") || lower.contains("rindu") -> "❤️"
            
            // Santai / Cool
            lower.contains("santai") || lower.contains("keren") || lower.contains("cool") || 
            lower.contains("relax") || lower.contains("chill") -> "😎"
            
            // Lelah / Stressed
            lower.contains("lelah") || lower.contains("capek") || lower.contains("stress") || 
            lower.contains("tired") || lower.contains("pusing") || lower.contains("penat") -> "😫"
            
            // Ngantuk / Sleepy
            lower.contains("tidur") || lower.contains("sleep") || lower.contains("ngantuk") || 
            lower.contains("sleepy") || lower.contains("rehat") -> "😴"
            
            // Sakit / Sick
            lower.contains("sakit") || lower.contains("sick") || lower.contains("mual") || 
            lower.contains("demam") || lower.contains("flu") -> "🤢"
            
            // Bingung / Thinking
            lower.contains("bingung") || lower.contains("mikir") || lower.contains("thinking") || 
            lower.contains("confused") || lower.contains("tanya") -> "🤔"
            
            // Makan / Food
            lower.contains("makan") || lower.contains("food") || lower.contains("lapar") || 
            lower.contains("hungry") || lower.contains("kenyang") || lower.contains("kuliner") -> "🍔"
            
            // Minum / Drink
            lower.contains("minum") || lower.contains("kopi") || lower.contains("cafe") || 
            lower.contains("haus") || lower.contains("teh") || lower.contains("juice") -> "☕"
            
            // Jalan-jalan / Travel
            lower.contains("terbang") || lower.contains("travel") || lower.contains("libur") || 
            lower.contains("jalan-jalan") || lower.contains("pantai") || lower.contains("gunung") -> "✈️"
            
            // Belanja / Shopping
            lower.contains("belanja") || lower.contains("beli") || lower.contains("mall") || 
            lower.contains("shop") || lower.contains("borong") -> "🛍️"
            
            // Olahraga / Sport
            lower.contains("lari") || lower.contains("run") || lower.contains("gym") || 
            lower.contains("olahraga") || lower.contains("sepeda") || lower.contains("bola") -> "🏃"
            
            // Kerja / Work
            lower.contains("kerja") || lower.contains("tugas") || lower.contains("kantor") || 
            lower.contains("work") || lower.contains("meeting") || lower.contains("proyek") -> "💼"
            
            // Pesta / Party
            lower.contains("pesta") || lower.contains("party") || lower.contains("ulang tahun") || 
            lower.contains("ultah") || lower.contains("celebrate") -> "🥳"

            // Default
            else -> "😊"
        }
    }
}
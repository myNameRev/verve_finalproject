package com.example.verv

import com.example.verv.data.MoodRepository
import org.junit.Assert.assertEquals
import org.junit.Test

class MoodRepositoryTest {

    @Test
    fun `test getEmojiFromTitle with happy keywords`() {
        // Menguji berbagai variasi kata senang
        assertEquals("😄", MoodRepository.getEmojiFromTitle("Hari ini sangat bahagia"))
        assertEquals("😄", MoodRepository.getEmojiFromTitle("Aku merasa senang"))
        assertEquals("😄", MoodRepository.getEmojiFromTitle("Ceritanya lucu sekali"))
    }

    @Test
    fun `test getEmojiFromTitle with sad keywords`() {
        // Menguji berbagai variasi kata sedih
        assertEquals("😭", MoodRepository.getEmojiFromTitle("Aku sedang sedih"))
        assertEquals("😭", MoodRepository.getEmojiFromTitle("Lagi galau nih"))
        assertEquals("😭", MoodRepository.getEmojiFromTitle("Patah hati banget"))
    }

    @Test
    fun `test getEmojiFromTitle with angry keywords`() {
        assertEquals("😡", MoodRepository.getEmojiFromTitle("Aku sangat marah"))
        assertEquals("😡", MoodRepository.getEmojiFromTitle("Lagi emosi"))
    }

    @Test
    fun `test getEmojiFromTitle with food and drink`() {
        assertEquals("🍔", MoodRepository.getEmojiFromTitle("Ayo kita makan"))
        assertEquals("☕", MoodRepository.getEmojiFromTitle("Minum kopi dulu"))
    }

    @Test
    fun `test getEmojiFromTitle with default emoji`() {
        // Menguji kata yang tidak ada di list
        assertEquals("😊", MoodRepository.getEmojiFromTitle("Halo apa kabar"))
    }
}
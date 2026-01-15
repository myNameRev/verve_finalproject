package com.example.verv

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.verv.data.MoodNote
import com.example.verv.data.MoodRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarFragment : Fragment() {

    private lateinit var adapter: MoodAdapter
    private var selectedDate: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        val rvMoods = view.findViewById<RecyclerView>(R.id.rv_calendar_moods)
        val tvSelected = view.findViewById<TextView>(R.id.tv_selected_date)
        val btnAddMood = view.findViewById<Button>(R.id.btn_add_mood_from_calendar)

        adapter = MoodAdapter(listOf()) { _, _ -> }
        rvMoods.layoutManager = LinearLayoutManager(context)
        rvMoods.adapter = adapter

        val dateFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())

        val todayCalendar = Calendar.getInstance()
        selectedDate = dateFormat.format(todayCalendar.time)
        tvSelected.text = "Moods on: $selectedDate"
        updateMoodsForSelectedDate()

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            selectedDate = dateFormat.format(calendar.time)
            tvSelected.text = "Moods on: $selectedDate"
            updateMoodsForSelectedDate()
        }

        btnAddMood.setOnClickListener {
            showAddMoodDialog()
        }

        return view
    }

    private fun showAddMoodDialog() {
        val editText = EditText(requireContext()).apply {
            hint = getString(R.string.add_mood)
        }

        val container = android.widget.FrameLayout(requireContext()).apply {
            val params = android.widget.FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(50, 0, 50, 0)
            layoutParams = params
            addView(editText)
        }

        AlertDialog.Builder(requireContext())
            .setTitle(R.string.add_mood)
            .setView(container)
            .setPositiveButton(R.string.app_name) { _, _ ->
                val title = editText.text.toString()
                if (title.isNotEmpty()) {
                    val emoji = MoodRepository.getEmojiFromTitle(title)
                    val newMood = MoodNote(title = title, emoji = emoji, date = selectedDate)

                    MoodRepository.addMood(newMood)
                    updateMoodsForSelectedDate()
                    Toast.makeText(context, R.string.mood_added, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, R.string.empty_error, Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun updateMoodsForSelectedDate() {
        try {
            val allMoods = MoodRepository.getAllMoods()
            val filteredList = allMoods.filter { it.date == selectedDate }
            adapter.updateData(filteredList)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        updateMoodsForSelectedDate()
    }
}
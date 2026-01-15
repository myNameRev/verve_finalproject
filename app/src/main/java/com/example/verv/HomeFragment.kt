package com.example.verv

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.verv.data.MoodNote
import com.example.verv.data.MoodRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var adapter: MoodAdapter
    private lateinit var rvMoodList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        
        rvMoodList = view.findViewById(R.id.rv_mood_list)
        
        adapter = MoodAdapter(MoodRepository.getAllMoods()) { mood, _ ->
            showOptionsDialog(mood)
        }

        rvMoodList.layoutManager = LinearLayoutManager(context)
        rvMoodList.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    private fun refreshData() {
        adapter.updateData(MoodRepository.getAllMoods())
    }

    private fun showOptionsDialog(mood: MoodNote) {
        val options = arrayOf(getString(R.string.edit_mood), getString(R.string.delete_mood))
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.app_name)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showEditDialog(mood)
                    1 -> showDeleteDialog(mood)
                }
            }
            .show()
    }

    private fun showDeleteDialog(mood: MoodNote) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_mood)
            .setMessage(getString(R.string.confirm_delete))
            .setPositiveButton(R.string.delete) { _, _ ->
                MoodRepository.deleteMood(mood)
                refreshData()
                Toast.makeText(context, R.string.mood_deleted, Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun showEditDialog(mood: MoodNote) {
        val editText = EditText(requireContext()).apply {
            setText(mood.title)
        }
        val container = android.widget.FrameLayout(requireContext())
        val params = android.widget.FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            leftMargin = 50
            rightMargin = 50
        }
        editText.layoutParams = params
        container.addView(editText)

        AlertDialog.Builder(requireContext())
            .setTitle(R.string.edit_mood)
            .setView(container)
            .setPositiveButton(R.string.save) { _, _ ->
                val newTitle = editText.text.toString()
                if (newTitle.isNotEmpty()) {
                    val newEmoji = MoodRepository.getEmojiFromTitle(newTitle)
                    val updatedMood = mood.copy(title = newTitle, emoji = newEmoji)

                    MoodRepository.updateMood(updatedMood)
                    refreshData()
                    Toast.makeText(context, R.string.mood_updated, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, R.string.empty_error, Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
}
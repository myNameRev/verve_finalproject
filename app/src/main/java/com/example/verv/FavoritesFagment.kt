package com.example.verv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.verv.data.MoodRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FavoritesFragment : Fragment() {

    private lateinit var adapter: MoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val rvMoodList = view.findViewById<RecyclerView>(R.id.rv_mood_list)


        adapter = MoodAdapter(MoodRepository.getFavorites()) { _, _ -> }

        rvMoodList.layoutManager = LinearLayoutManager(context)
        rvMoodList.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()

        if (::adapter.isInitialized) {
            adapter.updateData(MoodRepository.getFavorites())
        }
    }
}
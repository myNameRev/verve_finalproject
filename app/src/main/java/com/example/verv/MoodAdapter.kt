package com.example.verv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.verv.data.MoodNote
import com.example.verv.data.MoodRepository

class MoodAdapter(
    private var moodList: List<MoodNote>,
    private val onLongClick: (MoodNote, Int) -> Unit
) : RecyclerView.Adapter<MoodAdapter.MoodViewHolder>() {

    class MoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvEmoji: TextView = itemView.findViewById(R.id.tv_mood_emoji)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_mood_title)
        val ivFavorite: ImageView = itemView.findViewById(R.id.iv_favorite)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MoodViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_mood, viewGroup, false)
        return MoodViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MoodViewHolder, position: Int) {
        if (position >= moodList.size) return
        
        val moodNote = moodList[position]

        viewHolder.tvTitle.text = moodNote.title
        viewHolder.tvEmoji.text = moodNote.emoji


        try {
            if (moodNote.isFavorite) {
                viewHolder.ivFavorite.setImageResource(R.drawable.ic_heart_solid)
            } else {
                viewHolder.ivFavorite.setImageResource(R.drawable.ic_heart_outline)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        viewHolder.ivFavorite.setOnClickListener {

            val updatedMood = moodNote.copy(isFavorite = !moodNote.isFavorite)
            MoodRepository.updateMood(updatedMood)
            

            val newList = moodList.toMutableList()
            newList[position] = updatedMood
            moodList = newList
            notifyItemChanged(position)
        }

        viewHolder.itemView.setOnLongClickListener {
            onLongClick(moodNote, position)
            true
        }
    }

    override fun getItemCount() = moodList.size

    fun updateData(newList: List<MoodNote>) {
        moodList = newList
        notifyDataSetChanged()
    }
}
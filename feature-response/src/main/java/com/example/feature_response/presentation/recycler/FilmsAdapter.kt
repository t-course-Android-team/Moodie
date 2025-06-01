package com.example.feature_response.presentation.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.feature_response.R
import com.example.feature_response.databinding.FilmBinding
import com.example.feature_response.domain.FilmEntity

class FilmsAdapter : RecyclerView.Adapter<ViewHolder>() {

    lateinit var actionSave: ((List<FilmEntity>, Int) -> Unit)
    lateinit var actionSeen: ((List<FilmEntity>, Int) -> Unit)

    private var isButtonSeenEnabled: Boolean = true
    private var isButtonSavedEnabled: Boolean = true

    private val data = mutableListOf<FilmEntity>()

    fun updateButtonsConditions(isSeenEnabled: Boolean, isSavedEnabled: Boolean) {
        isButtonSavedEnabled = isSavedEnabled
        isButtonSeenEnabled = isSeenEnabled
    }

    fun getButtonSaveCondition(): Boolean = isButtonSavedEnabled
    fun getButtonSeenCondition(): Boolean = isButtonSeenEnabled

    fun setNewData(newData: List<FilmEntity>) {
        data.clear()
        data.addAll(newData)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = FilmBinding.inflate(LayoutInflater.from(viewGroup.context))
        return ViewHolder(binding).apply {
            save.setOnClickListener {
                actionSave(data, adapterPosition)
                seen.isEnabled = true
                seen.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.main))
                save.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.disabled))
                save.isEnabled = false
                isButtonSeenEnabled = true
                isButtonSavedEnabled = false
            }
            seen.setOnClickListener {
                actionSeen(data, adapterPosition)
                seen.isEnabled = false
                save.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.main))
                seen.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.disabled))
                save.isEnabled = true
                isButtonSeenEnabled = false
                isButtonSavedEnabled = true
            }
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) = with(viewHolder) {
        name.isSelected = true
        name.text = data[position].name
        duration.text = data[position].duration
        release.text = data[position].yearRelease.toString()
        genres.text = data[position].genres
        rate.text = data[position].rate.toString()
        if (data[position].rate < 7.0f) rate.setTextColor(ContextCompat.getColor(itemView.context, R.color.norm))
        if (data[position].rate < 5.0f) rate.setTextColor(ContextCompat.getColor(itemView.context, R.color.bad))
        if (data[position].rate >= 7.0f) rate.setTextColor(ContextCompat.getColor(itemView.context, R.color.good))
        plot.text = data[position].plot
        updatePoster(data[position].posterUrl)
        cardView.elevation = 6.0f
        if (position == 0) {
            save.isEnabled = isButtonSavedEnabled
            seen.isEnabled = isButtonSeenEnabled
            itemView.isClickable = true
            itemView.isFocusable = true
        } else {
            itemView.isClickable = false
            itemView.isFocusable = false
            itemView.isEnabled = false
        }
        if (seen.isEnabled && !save.isEnabled) {
            seen.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.main))
            save.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.disabled))
        } else if (!seen.isEnabled && save.isEnabled) {
            save.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.main))
            seen.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.disabled))
        }
    }

    override fun getItemCount() = data.size

    fun removeFilm(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getFilms(): List<FilmEntity> {
        return data
    }

}
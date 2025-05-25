package com.example.feature_response.presentation.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.feature_response.R
import com.example.feature_response.domain.FilmEntity

class FilmsAdapter : RecyclerView.Adapter<ViewHolder>() {

    private val data = mutableListOf<FilmEntity>()

    fun setNewData(newData: List<FilmEntity>) {
        data.clear()
        data.addAll(newData)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.film, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) = with(viewHolder) {
        dataWasSet = true
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
            name.isSelected = true
            itemView.isClickable = true
            itemView.isFocusable = true
        } else {
            itemView.isClickable = false
            itemView.isFocusable = false
            itemView.isEnabled = false
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
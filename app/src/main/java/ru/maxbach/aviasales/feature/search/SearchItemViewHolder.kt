package ru.maxbach.aviasales.feature.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.maxbach.aviasales.databinding.ItemSuggestionBinding
import ru.maxbach.aviasales.network.model.City

class SearchItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewBinding = ItemSuggestionBinding.bind(itemView)

    fun bind(city: City) {
        viewBinding.suggestionText.text = city.name
    }
}

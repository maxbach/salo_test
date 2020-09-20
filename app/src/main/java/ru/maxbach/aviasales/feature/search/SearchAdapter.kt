package ru.maxbach.aviasales.feature.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.maxbach.aviasales.R
import ru.maxbach.aviasales.network.model.City

class SearchAdapter(
        private val onCityClicked: (City) -> Unit
) : RecyclerView.Adapter<SearchItemViewHolder>() {

    companion object {
        private val itemCallback = object : DiffUtil.ItemCallback<City>() {
            override fun areItemsTheSame(oldItem: City, newItem: City): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: City, newItem: City): Boolean = oldItem == newItem
        }
    }

    private var differ = AsyncListDiffer(
            AdapterListUpdateCallback(this),
            AsyncDifferConfig.Builder(itemCallback).build()
    )

    fun submitList(newList: List<City>) {
        differ.submitList(newList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder = SearchItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_suggestion, parent, false)
    )

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.bind(differ.currentList[position])

        holder.itemView.setOnClickListener {
            onCityClicked.invoke(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

}

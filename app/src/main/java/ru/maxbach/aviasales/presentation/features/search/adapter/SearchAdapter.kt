package ru.maxbach.aviasales.presentation.features.search.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.maxbach.aviasales.databinding.ItemSearchHistoryHeaderBinding
import ru.maxbach.aviasales.databinding.ItemSuggestionBinding
import ru.maxbach.aviasales.utils.isInPortraitMode

class SearchAdapter(
    private val onCityClicked: (Long) -> Unit
) : AsyncListDifferDelegationAdapter<SearchItem>(itemCallback) {

    init {
        delegatesManager.addDelegate(getCityItemDelegate())
        delegatesManager.addDelegate(getHeaderDelegate())
    }

    companion object {
        private val itemCallback = object : DiffUtil.ItemCallback<SearchItem>() {
            override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean =
                oldItem is SearchItem.SuggestionItem
                    && newItem is SearchItem.SuggestionItem
                    && oldItem.id == newItem.id

            override fun areContentsTheSame(
              oldItem: SearchItem,
              newItem: SearchItem
            ): Boolean = oldItem == newItem
        }
    }

    private fun getCityItemDelegate() =
        adapterDelegateViewBinding<SearchItem.SuggestionItem, SearchItem, ItemSuggestionBinding>(
            { inflater, root -> ItemSuggestionBinding.inflate(inflater, root, false) }
        ) {
            bind {
                binding.apply {
                    root.setOnClickListener { onCityClicked(item.id) }
                    suggestionText.text = item.cityName
                }
            }
        }

    private fun getHeaderDelegate() =
        adapterDelegateViewBinding<SearchItem.SearchHistoryHeader, SearchItem, ItemSearchHistoryHeaderBinding>(
            { inflater, root ->
                ItemSearchHistoryHeaderBinding.inflate(inflater, root, false).apply {
                    this.root.setupLayoutParamsForFullWidthInAlbumOrientation()
                }
            }
        ) {}

    private fun View.setupLayoutParamsForFullWidthInAlbumOrientation() {
        if (!isInPortraitMode) {
            layoutParams = StaggeredGridLayoutManager.LayoutParams(layoutParams).apply {
                isFullSpan = true
            }
        }
    }

}

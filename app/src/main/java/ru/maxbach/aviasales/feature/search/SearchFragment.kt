package ru.maxbach.aviasales.feature.search

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import ru.maxbach.aviasales.R
import ru.maxbach.aviasales.databinding.FragmentSearchBinding
import ru.maxbach.aviasales.utils.showSoftInput
import ru.maxbach.aviasales.utils.viewBinding
import ru.maxbach.aviasales.utils.viewModels

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel by viewModels<SearchViewModel>()

    private val adapter = SearchAdapter { viewModel.onCityClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setup()

        viewModel.state.observe(viewLifecycleOwner, { state ->
            adapter.items = state.suggestions
        })
    }

    private fun FragmentSearchBinding.setup() {
        suggestionsList.adapter = adapter
        cityInput.doOnTextChanged { text, _, _, _ -> viewModel.onCityNewInput(text) }
        closeIcon.setOnClickListener {
            viewModel.closeScreen()
        }
        cityInput.showSoftInput()
    }

}

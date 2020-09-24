package ru.maxbach.aviasales.feature.search

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import ru.maxbach.aviasales.R
import ru.maxbach.aviasales.base.fragment.BaseFragment
import ru.maxbach.aviasales.databinding.FragmentSearchBinding
import ru.maxbach.aviasales.feature.search.adapter.SearchAdapter
import ru.maxbach.aviasales.utils.hideSoftInput
import ru.maxbach.aviasales.utils.showSoftInput
import ru.maxbach.aviasales.utils.viewBinding

class SearchFragment : BaseFragment<SearchScreenNavArgs, SearchViewModel>(R.layout.fragment_search) {

    companion object {
        fun create(navArgs: SearchScreenNavArgs) = SearchFragment().apply {
            putNavArgs(navArgs)
        }
    }

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel by viewModel<SearchViewModel>()

    private val adapter = SearchAdapter { viewModel.onCityClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setup()

        viewModel.state.observe(viewLifecycleOwner, { state ->
            adapter.items = state.suggestions
            binding.cityInput.hint = getString(state.inputHintId)
        })
    }

    override fun onStart() {
        super.onStart()
        binding.cityInput.showSoftInput()
    }

    override fun onStop() {
        super.onStop()
        requireActivity().hideSoftInput()
    }

    private fun FragmentSearchBinding.setup() {
        suggestionsList.adapter = adapter
        cityInput.doOnTextChanged { text, _, _, _ -> viewModel.onCityNewInput(text) }
        closeIcon.setOnClickListener {
            viewModel.closeScreen()
        }
        cityInput.setOnClickListener {
            cityInput.showSoftInput()
        }
    }

}

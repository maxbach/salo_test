package ru.maxbach.aviasales.feature.search

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import ru.maxbach.aviasales.R
import ru.maxbach.aviasales.base.viewmodel.viewModels
import ru.maxbach.aviasales.databinding.FragmentSearchBinding
import ru.maxbach.aviasales.feature.search.adapter.SearchAdapter
import ru.maxbach.aviasales.utils.showSoftInput
import ru.maxbach.aviasales.utils.viewBinding

class SearchFragment : Fragment(R.layout.fragment_search) {

    companion object {
        private const val NAV_ARGS_KEY = "nav_args"

        fun create(navArgs: SearchScreenNavArgs) = SearchFragment().apply {
            arguments = bundleOf(NAV_ARGS_KEY to navArgs)
        }
    }

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel by viewModels<SearchViewModel>()

    private val adapter = SearchAdapter { viewModel.onCityClicked(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.init(
            arguments?.getParcelable(NAV_ARGS_KEY)
                ?: throw IllegalStateException("Nav args not found. Use create() method to create fragment")
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setup()

        viewModel.state.observe(viewLifecycleOwner, { state ->
            adapter.items = state.suggestions
            binding.cityInput.hint = getString(state.inputHintId)
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

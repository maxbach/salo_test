package ru.maxbach.aviasales.feature.search

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import ru.maxbach.aviasales.R
import ru.maxbach.aviasales.databinding.FragmentSearchBinding
import ru.maxbach.aviasales.utils.viewBinding
import ru.maxbach.aviasales.utils.viewModels

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val binding by viewBinding { FragmentSearchBinding.bind(it) }
    private val viewModel by viewModels<SearchViewModel>()

    private val adapterFrom by lazy { ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line) }
    private val adapterTo by lazy { ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            cityFromField.setAdapter(adapterFrom)
            cityToField.setAdapter(adapterTo)

            goToPlanesButton.setOnClickListener {
                viewModel.buttonClicked()
            }
        }

        viewModel.state.observe(viewLifecycleOwner, { state ->
            adapterFrom.clear()
            adapterFrom.addAll(state.suggestionsFrom)

            adapterTo.clear()
            adapterTo.addAll(state.suggestionsTo)

            binding.goToPlanesButton.isEnabled = state.buttonEnabled
        })

    }
}

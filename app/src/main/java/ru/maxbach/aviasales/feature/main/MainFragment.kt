package ru.maxbach.aviasales.feature.main

import android.os.Bundle
import android.view.View
import ru.maxbach.aviasales.R
import ru.maxbach.aviasales.base.fragment.BaseFragment
import ru.maxbach.aviasales.base.fragment.EmptyState
import ru.maxbach.aviasales.databinding.FragmentMainBinding
import ru.maxbach.aviasales.utils.openTaxiApp
import ru.maxbach.aviasales.utils.viewBinding

class MainFragment : BaseFragment<EmptyState, MainScreenViewModel>(R.layout.fragment_main) {

    companion object {
        fun create() = MainFragment().apply {
            putNavArgs()
        }
    }

    private val viewModel by viewModel<MainScreenViewModel>()
    private val viewBinding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding) {
            cityFromField.setOnClickListener { viewModel.onCityFromClicked() }
            cityToField.setOnClickListener { viewModel.onCityToClicked() }
            goToPlanesButton.setOnClickListener { viewModel.onButtonClicked() }
        }

        viewModel.state.observe(viewLifecycleOwner, { newState ->
            with(viewBinding) {
                cityFromField.text = newState.cityFrom
                cityToField.text = newState.cityTo
            }
        })

        viewModel.openTaxiEvent.observe(viewLifecycleOwner) {
            requireActivity().openTaxiApp()
        }
    }

}

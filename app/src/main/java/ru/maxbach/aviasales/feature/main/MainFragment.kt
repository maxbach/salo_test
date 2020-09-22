package ru.maxbach.aviasales.feature.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.maxbach.aviasales.R
import ru.maxbach.aviasales.databinding.FragmentMainBinding
import ru.maxbach.aviasales.utils.viewBinding
import ru.maxbach.aviasales.utils.viewModels

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel by viewModels<MainScreenViewModel>()
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
    }

}

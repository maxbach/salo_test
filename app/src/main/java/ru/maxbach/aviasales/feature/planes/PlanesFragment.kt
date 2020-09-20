package ru.maxbach.aviasales.feature.planes

import androidx.fragment.app.Fragment
import ru.maxbach.aviasales.R
import ru.maxbach.aviasales.network.model.City

class PlanesFragment : Fragment(R.layout.fragment_planes) {

    companion object {
        //TODO: add logic to save City
        fun create(cityFrom: City, cityTo: City): PlanesFragment = PlanesFragment()
    }

}

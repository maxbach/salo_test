package ru.maxbach.aviasales.presentation.features.map.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import ru.maxbach.aviasales.R
import ru.maxbach.aviasales.utils.toBitmap


@SuppressLint("InflateParams")
fun Context.createCityMarker(cityShortName: String): BitmapDescriptor = (LayoutInflater
    .from(this)
    .inflate(R.layout.map_city_marker, null) as TextView)
    .apply { text = cityShortName }
    .toBitmap()
    .let(BitmapDescriptorFactory::fromBitmap)

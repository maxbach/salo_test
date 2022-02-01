package ru.maxbach.aviasales.domain.models

import com.squareup.moshi.JsonClass
import ru.maxbach.aviasales.data.datasource.network.model.City

//move to models
@JsonClass(generateAdapter = true)
data class LastSearch(
  val cityFrom: City,
  val cityTo: City
)

package ru.maxbach.aviasales.feature.main

data class MainScreenState(
        val cityFrom: String? = null,
        val cityTo: String? = null,
        val buttonEnabled: Boolean = false
)

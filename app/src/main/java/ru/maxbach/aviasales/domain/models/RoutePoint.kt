package ru.maxbach.aviasales.domain.models

import androidx.annotation.IdRes
import ru.maxbach.aviasales.R

enum class RoutePoint(@IdRes val inputHintId: Int) {
    FROM(R.string.global_input_from_hint),
    TO(R.string.global_input_to_hint)
}

package ru.maxbach.aviasales.domain

import io.reactivex.rxjava3.core.Single
import ru.maxbach.aviasales.data.SuggestionsRepository
import ru.maxbach.aviasales.network.model.City
import javax.inject.Inject

class UploadSuggestionsUseCase @Inject constructor(
        private val repository: SuggestionsRepository
) {

    //TODO: add logic for choosing language
    operator fun invoke(input: String): Single<List<City>> = if (input.length >= 3) {
        repository.getSuggestions(input, "ru")
    } else {
        Single.just(emptyList())
    }

}

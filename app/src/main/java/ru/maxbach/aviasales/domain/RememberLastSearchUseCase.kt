package ru.maxbach.aviasales.domain

import io.reactivex.rxjava3.core.Completable
import ru.maxbach.aviasales.data.LastSearchRepository
import ru.maxbach.aviasales.datasource.network.model.City
import ru.maxbach.aviasales.domain.models.LastSearch
import javax.inject.Inject

class RememberLastSearchUseCase @Inject constructor(
    private val repository: LastSearchRepository
) {

    operator fun invoke(cityFrom: City, cityTo: City): Completable = repository
        .rememberLastSearch(LastSearch(cityFrom, cityTo))

}

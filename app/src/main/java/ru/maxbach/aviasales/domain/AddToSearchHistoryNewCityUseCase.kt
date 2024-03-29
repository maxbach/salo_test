package ru.maxbach.aviasales.domain

import io.reactivex.rxjava3.core.Completable
import ru.maxbach.aviasales.data.repo.SearchHistoryRepository
import ru.maxbach.aviasales.data.datasource.network.model.City
import javax.inject.Inject

class AddToSearchHistoryNewCityUseCase @Inject constructor(
        private val repository: SearchHistoryRepository
) {

    operator fun invoke(city: City): Completable = repository.addCityToSearchHistory(city)

}

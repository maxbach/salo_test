package ru.maxbach.aviasales.datasource.in_memory

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.maxbach.aviasales.base.preferences.RxJsonSharedPreferences
import ru.maxbach.aviasales.datasource.network.model.City
import ru.maxbach.aviasales.datasource.network.model.Location
import ru.maxbach.aviasales.domain.models.LastSearch
import javax.inject.Inject

class LastSearchInMemoryDataSource @Inject constructor(
    private val rxJsonSharedPreferences: RxJsonSharedPreferences
) {

    companion object {
        private const val LAST_SEARCH_KEY = "last_search"

        private val defaultCityFrom = City(
            id = 12156,
            fullName = "Находка, Россия",
            location = Location(latitude = 42.83333, longitude = 132.894722),
            name = "Находка"
        )
        private val defaultCityTo = City(
            id = 12196,
            fullName = "Санкт-Петербург, Россия",
            location = Location(latitude = 59.92922, longitude = 30.30805),
            name = "Санкт-Петербург"
        )

        private val defaultLastSearch = LastSearch(defaultCityFrom, defaultCityTo)
    }

    fun getLastSearchValue(): Single<LastSearch> = rxJsonSharedPreferences
        .getJsonObjectOrDefault(
            LAST_SEARCH_KEY,
            LastSearch::class.java,
            defaultLastSearch
        )

    fun writeLastSearch(lastSearch: LastSearch): Completable = rxJsonSharedPreferences
        .writeJsonObject(
            LAST_SEARCH_KEY,
            LastSearch::class.java,
            lastSearch
        )

}

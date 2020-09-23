package ru.maxbach.aviasales.datasource

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.maxbach.aviasales.network.model.City
import javax.inject.Inject

class SearchHistoryInMemoryDataSource @Inject constructor(
    private val rxJsonSharedPreferences: RxJsonSharedPreferences
) {

    companion object {
        private const val SEARCH_HISTORY_KEY = "search_history"
    }

    fun getSearchHistory(): Single<SearchHistory> = rxJsonSharedPreferences
        .getJsonObjectOrDefault(
            SEARCH_HISTORY_KEY,
            SearchHistory::class.java,
            SearchHistory()
        )

    fun addNewCityToSearchHistory(newCity: City): Completable = rxJsonSharedPreferences
        .updateJsonObject(
            SEARCH_HISTORY_KEY,
            SearchHistory::class.java
        ) { oldSearchHistory ->
            oldSearchHistory
                ?.addNewCityAndRemoveRepeats(newCity)
                ?: SearchHistory(listOf(newCity))
        }

}

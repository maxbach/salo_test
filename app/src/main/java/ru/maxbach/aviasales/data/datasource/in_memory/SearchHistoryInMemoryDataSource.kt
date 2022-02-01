package ru.maxbach.aviasales.data.datasource.in_memory

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.maxbach.aviasales.data.datasource.in_memory.base.RxJsonSharedPreferences
import ru.maxbach.aviasales.data.datasource.network.model.City
import ru.maxbach.aviasales.domain.models.SearchHistory
import ru.maxbach.aviasales.domain.models.addNewCityAndRemoveRepeats
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

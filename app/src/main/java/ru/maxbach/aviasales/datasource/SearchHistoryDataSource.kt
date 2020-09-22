package ru.maxbach.aviasales.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.maxbach.aviasales.network.model.City
import javax.inject.Inject

class SearchHistoryDataSource @Inject constructor(
        private val sharedPreferences: SharedPreferences,
        private val moshi: Moshi
) {

    companion object {
        private const val HISTORY_SEARCH_KEY = "history_search"
    }

    fun getSearchHistory(): Single<List<City>> = Single
            .fromCallable {
                return@fromCallable sharedPreferences
                        .getString(HISTORY_SEARCH_KEY, null)
                        ?.let {
                            getJsonAdapter().fromJson(it)
                        } ?: emptyList()
            }
            .subscribeOn(Schedulers.io())

    fun addToSearchHistory(city: City): Completable = getSearchHistory()
            .flatMapCompletable { searchHistory ->
                Completable
                        .fromAction {
                            sharedPreferences.edit {
                                val newList = listOf(city) + (searchHistory - city)
                                putString(HISTORY_SEARCH_KEY, getJsonAdapter().toJson(newList))
                            }
                        }
            }
            .subscribeOn(Schedulers.io())

    private fun getJsonAdapter(): JsonAdapter<List<City>> = moshi
            .adapter<List<City>>(Types.newParameterizedType(List::class.java, City::class.java))
}

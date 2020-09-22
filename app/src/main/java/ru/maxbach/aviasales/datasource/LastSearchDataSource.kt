package ru.maxbach.aviasales.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import com.squareup.moshi.Moshi
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.maxbach.aviasales.network.model.City
import ru.maxbach.aviasales.network.model.Location
import javax.inject.Inject

class LastSearchDataSource @Inject constructor(
        private val sharedPreferences: SharedPreferences,
        private val moshi: Moshi
) {

    companion object {
        private const val LAST_SEARCH_KEY = "last_search"
        private val DEFAULT_LAST_SEARCH = LastSearch(
                cityFrom = City(
                        id = 12156,
                        fullName = "Находка, Россия",
                        location = Location(latitude = 42.83333, longitude = 132.894722),
                        aliases = emptyList(),
                        name = "Находка"
                ),
                cityTo = City(
                        id = 12196,
                        fullName = "Санкт-Петербург, Россия",
                        location = Location(latitude = 59.92922, longitude = 30.30805),
                        aliases = listOf("LED"),
                        name = "Санкт-Петербург"
                )
        )
    }

    fun getLastSearchValue(): Single<LastSearch> = Single
            .fromCallable {
                return@fromCallable sharedPreferences.getString(
                        LAST_SEARCH_KEY,
                        null
                )?.let {
                    moshi.adapter(LastSearch::class.java).fromJson(it)
                } ?: DEFAULT_LAST_SEARCH
            }
            .subscribeOn(Schedulers.io())

    fun writeLastSearchValue(lastSearch: LastSearch): Completable = Completable
            .fromCallable {
                sharedPreferences.edit {
                    putString(LAST_SEARCH_KEY, moshi.adapter(LastSearch::class.java).toJson(lastSearch))
                    commit()
                }
            }
            .subscribeOn(Schedulers.io())

}

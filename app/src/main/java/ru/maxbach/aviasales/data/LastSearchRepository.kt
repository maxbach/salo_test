package ru.maxbach.aviasales.data

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.maxbach.aviasales.datasource.LastSearch
import ru.maxbach.aviasales.datasource.LastSearchInMemoryDataSource
import javax.inject.Inject

class LastSearchRepository @Inject constructor(
    private val inMemoryDataSource: LastSearchInMemoryDataSource
) {

    fun getLastSearch(): Single<LastSearch> = inMemoryDataSource
        .getLastSearchValue()

    fun rememberLastSearch(newLastSearch: LastSearch): Completable = inMemoryDataSource
        .writeLastSearch(newLastSearch)
}

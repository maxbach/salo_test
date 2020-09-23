package ru.maxbach.aviasales.data

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.maxbach.aviasales.datasource.in_memory.LastSearchInMemoryDataSource
import ru.maxbach.aviasales.domain.models.LastSearch
import javax.inject.Inject

class LastSearchRepository @Inject constructor(
    private val inMemoryDataSource: LastSearchInMemoryDataSource
) {

    fun getLastSearch(): Single<LastSearch> = inMemoryDataSource
        .getLastSearchValue()

    fun rememberLastSearch(newLastSearch: LastSearch): Completable = inMemoryDataSource
        .writeLastSearch(newLastSearch)
}

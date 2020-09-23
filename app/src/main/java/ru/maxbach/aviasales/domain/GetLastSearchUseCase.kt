package ru.maxbach.aviasales.domain

import io.reactivex.rxjava3.core.Single
import ru.maxbach.aviasales.data.LastSearchRepository
import ru.maxbach.aviasales.domain.models.LastSearch
import javax.inject.Inject

class GetLastSearchUseCase @Inject constructor(
    private val repository: LastSearchRepository
) {

    operator fun invoke(): Single<LastSearch> = repository
        .getLastSearch()

}

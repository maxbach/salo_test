package ru.maxbach.aviasales.feature.search

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import ru.maxbach.aviasales.base.viewmodel.BaseViewModel
import ru.maxbach.aviasales.domain.UploadSuggestionsUseCase
import ru.maxbach.aviasales.navigation.ScreenResult
import ru.maxbach.aviasales.network.model.City
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchViewModel @Inject constructor(
        private val coordinator: SearchCoordinator,
        private val uploadSuggestionsUseCase: UploadSuggestionsUseCase,
        private val screenResult: ScreenResult<City>
) : BaseViewModel<SearchScreenState>(SearchScreenState()) {

    private val cityInputSubject = BehaviorSubject.create<String>()

    init {
        cityInputSubject
                .debounce(500, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMapSingle(uploadSuggestionsUseCase::invoke)
                .observeOn(AndroidSchedulers.mainThread())
                .untilDestroy(onNext = { newSuggestions ->
                    updateState { SearchScreenState(newSuggestions) }
                })
    }

    fun onCityNewInput(newText: CharSequence?) {
        newText?.let { cityInputSubject.onNext(it.toString()) }
    }

    fun onCityClicked(city: City) {
        screenResult.onNext(city)
        coordinator.close()
    }

}

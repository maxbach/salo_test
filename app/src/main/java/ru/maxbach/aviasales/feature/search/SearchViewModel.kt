package ru.maxbach.aviasales.feature.search

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import ru.maxbach.aviasales.base.viewmodel.BaseViewModel
import ru.maxbach.aviasales.domain.ConvertSuggestionsToUiItemsUseCase
import ru.maxbach.aviasales.domain.GetSuggestionsUseCase
import ru.maxbach.aviasales.navigation.ScreenResult
import ru.maxbach.aviasales.network.model.City
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val coordinator: SearchCoordinator,
    private val getSuggestionsUseCase: GetSuggestionsUseCase,
    private val screenResult: ScreenResult<SearchResult>,
    private val convertSuggestionsToUiItemsUseCase: ConvertSuggestionsToUiItemsUseCase
) : BaseViewModel<SearchScreenState>(SearchScreenState()) {

    private val cityInputSubject = BehaviorSubject.createDefault("")

    private lateinit var cities: List<City>
    private lateinit var navArgs: SearchScreenNavArgs

    init {
        observeCityInput()
    }

    fun init(navArgs: SearchScreenNavArgs) {
        this.navArgs = navArgs

        updateState { currentState ->
            currentState.copy(inputHintId = navArgs.routePoint.inputHintId)
        }
    }

    fun onCityNewInput(newText: CharSequence?) {
        newText?.let { cityInputSubject.onNext(it.toString()) }
    }

    fun onCityClicked(cityId: Long) {
        val chosenCity = cities.find { it.id == cityId }
            ?: throw IllegalStateException("There is no such city in cached")

        screenResult.onNext(SearchResult(chosenCity, navArgs.routePoint))
        coordinator.close()
    }

    fun closeScreen() {
        coordinator.close()
    }

    private fun observeCityInput() {
        cityInputSubject
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMapSingle(getSuggestionsUseCase::invoke)
            .observeOn(AndroidSchedulers.mainThread())
            .untilDestroy(onNext = { newSuggestions ->
                cities = newSuggestions.cities

                val viewItems = convertSuggestionsToUiItemsUseCase.invoke(newSuggestions)
                updateState { currentState -> currentState.copy(suggestions = viewItems) }
            })
    }

}

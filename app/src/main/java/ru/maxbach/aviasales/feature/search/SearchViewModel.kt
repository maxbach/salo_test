package ru.maxbach.aviasales.feature.search

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import ru.maxbach.aviasales.base.viewmodel.BaseViewModel
import ru.maxbach.aviasales.domain.AddToSearchHistoryNewCityUseCase
import ru.maxbach.aviasales.domain.GetSuggestionsUseCase
import ru.maxbach.aviasales.navigation.ScreenResult
import ru.maxbach.aviasales.network.model.City
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchViewModel @Inject constructor(
        private val coordinator: SearchCoordinator,
        private val getSuggestionsUseCase: GetSuggestionsUseCase,
        private val screenResult: ScreenResult<City>,
        private val addToSearchHistoryNewCityUseCase: AddToSearchHistoryNewCityUseCase
) : BaseViewModel<SearchScreenState>(SearchScreenState()) {

    private val cityInputSubject = BehaviorSubject.createDefault("")

    private var cachedCities: List<City> = emptyList()

    init {
        //TODO: make it clever
        cityInputSubject
                .debounce(500, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMapSingle { newInput ->
                    getSuggestionsUseCase.invoke(newInput).map { suggestions -> newInput to suggestions }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .untilDestroy(onNext = { (newInput, newSuggestions) ->
                    cachedCities = newSuggestions

                    val viewItems = listOfNotNull(
                            SearchItem.SearchHistoryHeader.takeIf { newInput.isBlank() && newSuggestions.isNotEmpty() }
                    ) + newSuggestions.map { SearchItem.SuggestionItem(it.id, it.name) }

                    updateState { SearchScreenState(viewItems) }
                })
    }

    fun onCityNewInput(newText: CharSequence?) {
        newText?.let { cityInputSubject.onNext(it.toString()) }
    }

    fun onCityClicked(cityId: Long) {
        //TODO: write correct exception
        val chosenCity = cachedCities.find { it.id == cityId }!!

        //TODO: maybe it will not works
        addToSearchHistoryNewCityUseCase
                .invoke(chosenCity)
                .untilDestroy()

        screenResult.onNext(chosenCity)
        coordinator.close()
    }

    fun closeScreen() {
        coordinator.close()
    }

}

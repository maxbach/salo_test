package ru.maxbach.aviasales.feature.search

import androidx.lifecycle.MutableLiveData
import ru.maxbach.aviasales.base.viewmodel.RxViewModel
import ru.maxbach.aviasales.utils.toImmutable
import javax.inject.Inject

class SearchViewModel @Inject constructor(
        private val coordinator: SearchCoordinator
) : RxViewModel() {

    private val getMockedList = listOf(
            "Москва",
            "Питер",
            "Находка",
            "Владик",
            "Хабаровск",
            "Минск",
            "Буденовск"
    )

    private val _state = MutableLiveData<SearchScreenState>()
    val state = _state.toImmutable()

    init {
        _state.value = SearchScreenState(
                suggestionsFrom = getMockedList,
                suggestionsTo = getMockedList,
                buttonEnabled = true
        )
    }

    fun buttonClicked() {
        coordinator.openPlanes()
    }


}

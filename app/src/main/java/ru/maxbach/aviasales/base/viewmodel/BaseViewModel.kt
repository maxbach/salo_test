package ru.maxbach.aviasales.base.viewmodel

import androidx.lifecycle.MutableLiveData
import ru.maxbach.aviasales.utils.toImmutable

open class BaseViewModel<TState>(private val initialState: TState) : RxViewModel() {

    protected val _state = MutableLiveData(initialState)
    val state = _state.toImmutable()

    protected val currentState: TState
        get() = _state.value ?: initialState

    protected fun updateState(action: (currentState: TState) -> TState) {
        _state.value = action(currentState)
    }

}

package ru.maxbach.aviasales.base.viewmodel

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import ru.maxbach.aviasales.base.fragment.BaseFragment
import ru.maxbach.aviasales.utils.toImmutable

open class BaseViewModel<TState, NavArgs : Parcelable>(
    private val initialState: TState,
    handle: SavedStateHandle
) : RxViewModel() {

    protected val navArgs: NavArgs = handle.get(BaseFragment.NAV_ARGS_KEY)
        ?: throw IllegalStateException("Nav args not found.")

    protected val _state = MutableLiveData(initialState)
    val state = _state.toImmutable()

    protected val currentState: TState
        get() = _state.value ?: initialState

    protected fun updateState(action: (currentState: TState) -> TState) {
        _state.value = action(currentState)
    }

}

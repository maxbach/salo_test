package ru.maxbach.aviasales.base.viewmodel

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import ru.maxbach.aviasales.base.fragment.BaseFragment
import ru.maxbach.aviasales.utils.toImmutable

open class BaseViewModel<TState, NavArgs : Parcelable>(
    private val initialState: TState,
    arg0: SavedStateHandle
) : RxViewModel() {

    protected val navArgs: NavArgs = arg0.get(BaseFragment.NAV_ARGS_KEY)
        ?: throw IllegalStateException("Nav args not found. Use putNavArgs() in BaseFragment method to add arguments to bundle.")

    protected val _state = MutableLiveData(initialState)
    val state = _state.toImmutable()

    protected val currentState: TState
        get() = _state.value ?: initialState

    protected fun updateState(action: (currentState: TState) -> TState) {
        _state.value = action(currentState)
    }

}

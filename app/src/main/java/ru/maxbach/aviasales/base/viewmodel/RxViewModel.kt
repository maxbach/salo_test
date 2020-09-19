package ru.maxbach.aviasales.base.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel

/**
 * Base class of ViewModel with [io.reactivex.disposables.Disposable] handling.
 */
open class RxViewModel(
        private val destroyable: BaseDestroyable = BaseDestroyable()
) : ViewModel(), Destroyable by destroyable {

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        destroyable.onDestroy()
    }

}

package ru.maxbach.aviasales.presentation.base.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel

/**
 * Base class of ViewModel with Disposable handling.
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

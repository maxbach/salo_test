package ru.maxbach.aviasales.base.viewmodel

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.maxbach.aviasales.di.viewmodel.ViewModelFactoryProvider

object LifecycleViewModelProviders {

    /**
     * Returns ViewModelProvider.Factory instance from current lifecycleOwner.
     * Search #ViewModelFactoryProvider are produced according to priorities:
     * 1. Fragment;
     * 2. Parent fragment recursively;
     * 3. Activity;
     * 4. Application.
     */
    fun getViewModelFactory(provider: Any): ViewModelProvider.Factory =
        when (provider) {
            is ViewModelFactoryProvider -> provider.viewModelFactory
            is Fragment -> getViewModelFactory(
                provider.parentFragment ?: provider.requireActivity()
            )
            is Activity -> getViewModelFactory(provider.application)
            else -> throw IllegalArgumentException("View model factory not found.")
        }

}

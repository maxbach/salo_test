package ru.maxbach.aviasales.di.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

interface ViewModelAssistedFactory<VM : ViewModel> {
    fun create(arg0: SavedStateHandle): VM
}

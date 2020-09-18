package ru.maxbach.aviasales.di.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.maxbach.aviasales.MainActivityViewModel

@Suppress("detekt.TooManyFunctions")
@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    fun mainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel

}

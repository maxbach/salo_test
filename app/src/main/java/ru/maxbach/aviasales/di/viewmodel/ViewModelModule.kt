package ru.maxbach.aviasales.di.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.maxbach.aviasales.feature.main.MainScreenViewModel
import ru.maxbach.aviasales.feature.plane.PlaneViewModel
import ru.maxbach.aviasales.feature.search.SearchViewModel

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainScreenViewModel::class)
    fun bindMainScreenViewModel(mainScreenViewModel: MainScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlaneViewModel::class)
    fun bindPlaneViewModel(planeViewModel: PlaneViewModel): ViewModel

}

package ru.maxbach.aviasales.di.viewmodel

import androidx.lifecycle.ViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.maxbach.aviasales.presentation.features.main.MainScreenViewModel
import ru.maxbach.aviasales.presentation.features.map.MapScreenViewModel
import ru.maxbach.aviasales.presentation.features.search.SearchViewModel

@Module(includes = [ViewModelAssistedFactoriesModule::class])
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainScreenViewModel::class)
    fun bindMainScreenViewModel(viewModel: MainScreenViewModel.Factory): ViewModelAssistedFactory<out ViewModel>

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun bindSearchViewModel(viewModel: SearchViewModel.Factory): ViewModelAssistedFactory<out ViewModel>

    @Binds
    @IntoMap
    @ViewModelKey(MapScreenViewModel::class)
    fun bindMapScreenViewModel(viewModel: MapScreenViewModel.Factory): ViewModelAssistedFactory<out ViewModel>

}

@AssistedModule
@Module(includes = [AssistedInject_ViewModelAssistedFactoriesModule::class])
abstract class ViewModelAssistedFactoriesModule

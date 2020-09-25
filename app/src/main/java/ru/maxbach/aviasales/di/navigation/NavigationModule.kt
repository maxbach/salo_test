package ru.maxbach.aviasales.di.navigation

import dagger.Module
import dagger.Provides
import ru.maxbach.aviasales.navigation.ScreenResult
import ru.maxbach.aviasales.presentation.search.SearchResult
import javax.inject.Singleton

@Module
class NavigationModule {

    @Provides
    @Singleton
    fun provideCityScreenResult(): ScreenResult<SearchResult> = ScreenResult()

}

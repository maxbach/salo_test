package ru.maxbach.aviasales.di.navigation

import dagger.Module
import dagger.Provides
import ru.maxbach.aviasales.feature.search.SearchResult
import ru.maxbach.aviasales.navigation.ScreenResult
import javax.inject.Singleton

@Module
class NavigationModule {

    @Provides
    @Singleton
    fun provideCityScreenResult(): ScreenResult<SearchResult> = ScreenResult()

}

package ru.maxbach.aviasales.di.navigation

import dagger.Module
import dagger.Provides
import ru.maxbach.aviasales.navigation.ScreenResult
import ru.maxbach.aviasales.network.model.City
import javax.inject.Singleton

@Module
class NavigationModule {

    @Provides
    @Singleton
    fun provideCityScreenResult(): ScreenResult<City> = ScreenResult()

}

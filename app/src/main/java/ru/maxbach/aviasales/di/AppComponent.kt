package ru.maxbach.aviasales.di

import dagger.Component
import ru.maxbach.aviasales.App
import ru.maxbach.aviasales.MainActivity
import ru.maxbach.aviasales.di.navigation.CiceroneModule
import ru.maxbach.aviasales.di.network.NetworkModule
import ru.maxbach.aviasales.di.viewmodel.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class, CiceroneModule::class, NetworkModule::class])
interface AppComponent {
    fun inject(app: App)

    fun inject(activity: MainActivity)
}

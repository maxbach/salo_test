package ru.maxbach.aviasales.di

import dagger.Component
import ru.maxbach.aviasales.App
import ru.maxbach.aviasales.di.viewmodel.ViewModelModule

@Component(modules = [ViewModelModule::class])
interface AppComponent {
    fun inject(app: App)
}

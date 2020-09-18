package ru.maxbach.aviasales

import android.app.Application
import ru.maxbach.aviasales.di.DaggerAppComponent
import ru.maxbach.aviasales.di.viewmodel.ViewModelFactory
import ru.maxbach.aviasales.di.viewmodel.ViewModelFactoryProvider
import javax.inject.Inject

class App : Application(), ViewModelFactoryProvider {

    @Inject
    override lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent
                .create()
                .inject(this)
    }

}

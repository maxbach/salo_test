package ru.maxbach.aviasales

import android.app.Application
import androidx.lifecycle.ViewModel
import ru.maxbach.aviasales.di.AppComponent
import ru.maxbach.aviasales.di.DaggerAppComponent
import ru.maxbach.aviasales.di.viewmodel.ViewModelAssistedFactory
import timber.log.Timber
import javax.inject.Inject

class App : Application() {

    @Inject
    lateinit var viewModelMap: MutableMap<Class<out ViewModel>, ViewModelAssistedFactory<out ViewModel>>

    val component: AppComponent by lazy {
        DaggerAppComponent
            .factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()

        component.inject(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}

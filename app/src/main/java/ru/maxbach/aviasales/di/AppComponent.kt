package ru.maxbach.aviasales.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.maxbach.aviasales.App
import ru.maxbach.aviasales.MainActivity
import ru.maxbach.aviasales.di.in_memory.PreferencesModule
import ru.maxbach.aviasales.di.math.MathModule
import ru.maxbach.aviasales.di.navigation.CiceroneModule
import ru.maxbach.aviasales.di.navigation.NavigationModule
import ru.maxbach.aviasales.di.network.NetworkModule
import ru.maxbach.aviasales.di.viewmodel.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ViewModelModule::class,
        CiceroneModule::class,
        NetworkModule::class,
        NavigationModule::class,
        PreferencesModule::class,
        MathModule::class
    ]
)
interface AppComponent {

    fun inject(app: App)

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

}

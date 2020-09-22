package ru.maxbach.aviasales.di.memory

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module
class PreferencesModule {

    @Provides
    fun providesSharedPreferences(context: Context): SharedPreferences = context.getSharedPreferences(
            "main",
            Context.MODE_PRIVATE
    )

}

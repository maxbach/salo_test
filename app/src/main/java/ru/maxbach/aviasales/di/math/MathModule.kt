package ru.maxbach.aviasales.di.math

import dagger.Module
import dagger.Provides

@Module
class MathModule {

    @Provides
    @DotsInCurveCount
    fun provideDotsCount(): Int = 1000

}

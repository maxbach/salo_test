package ru.maxbach.aviasales.di.network

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.maxbach.aviasales.network.CitySearchApi
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providePublicClient(): OkHttpClient = OkHttpClient
            .Builder()
            .build()

    @Singleton
    @Provides
    fun provideMoshi() = Moshi
            .Builder()
            .build()

    @Singleton
    @Provides
    fun provideRetrofitFactory(client: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
            .baseUrl("https://yasen.hotellook.com")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()


    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): CitySearchApi = retrofit.create(CitySearchApi::class.java)

}

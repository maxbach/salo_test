package ru.maxbach.aviasales.di.network

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.maxbach.aviasales.BuildConfig
import ru.maxbach.aviasales.network.SuggestionsApi
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providePublicClient(): OkHttpClient = OkHttpClient
        .Builder()
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
        }
        .build()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi
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
    fun provideApi(retrofit: Retrofit): SuggestionsApi = retrofit.create(SuggestionsApi::class.java)

}

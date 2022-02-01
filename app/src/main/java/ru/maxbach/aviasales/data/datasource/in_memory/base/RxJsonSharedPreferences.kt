package ru.maxbach.aviasales.data.datasource.in_memory.base

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.Optional
import javax.inject.Inject

class RxJsonSharedPreferences @Inject constructor(
    private val jsonSharedPreferences: JsonSharedPreferences
) {

    fun <T> getJsonObject(
        key: String,
        clazz: Class<T>
    ): Single<Optional<T>> = wrapActionToRxSingleOnIo {
        Optional.ofNullable(jsonSharedPreferences.getJsonObject(key, clazz))
    }

    fun <T> getJsonObjectOrDefault(
        key: String,
        clazz: Class<T>,
        defaultValue: T
    ): Single<T> = wrapActionToRxSingleOnIo {
        jsonSharedPreferences.getJsonObjectOrDefault(key, clazz, defaultValue)
    }

    fun <T> writeJsonObject(
        key: String,
        clazz: Class<T>,
        newValue: T
    ): Completable = wrapActionToRxCompletableOnIo {
        jsonSharedPreferences.writeJsonObject(key, clazz, newValue)
    }

    fun <T> updateJsonObject(
        key: String,
        clazz: Class<T>,
        updateAction: (T?) -> T
    ): Completable = wrapActionToRxCompletableOnIo {
        jsonSharedPreferences.updateJsonObject(key, clazz, updateAction)
    }

    private fun <T> wrapActionToRxSingleOnIo(action: () -> T): Single<T> = Single
        .fromCallable(action)
        .subscribeOn(Schedulers.io())

    private fun wrapActionToRxCompletableOnIo(action: () -> Unit): Completable = Completable
        .fromCallable(action)
        .subscribeOn(Schedulers.io())

}

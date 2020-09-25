package ru.maxbach.aviasales.presentation.base.viewmodel

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class BaseDestroyable : Destroyable {

    private val subscriptions = CompositeDisposable()

    override fun clearSubscriptions() = subscriptions.clear()

    /**
     * Call it on parent's onDestroy method.
     */
    fun onDestroy() = subscriptions.dispose()

    override fun <T> Flowable<T>.untilDestroy(
            onNext: (T) -> Unit,
            onError: (Throwable) -> Unit,
            onComplete: () -> Unit
    ): Disposable = observeOn(AndroidSchedulers.mainThread())
            .subscribe(onNext, onError, onComplete)
            .also { subscriptions.add(it) }

    override fun <T> Observable<T>.untilDestroy(
            onNext: (T) -> Unit,
            onError: (Throwable) -> Unit,
            onComplete: () -> Unit
    ): Disposable = observeOn(AndroidSchedulers.mainThread())
            .subscribe(onNext, onError, onComplete)
            .also { subscriptions.add(it) }

    override fun <T> Single<T>.untilDestroy(
            onSuccess: (T) -> Unit,
            onError: (Throwable) -> Unit
    ): Disposable = observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)
            .also { subscriptions.add(it) }

    override fun Completable.untilDestroy(
            onComplete: () -> Unit,
            onError: (Throwable) -> Unit
    ): Disposable = observeOn(AndroidSchedulers.mainThread())
            .subscribe(onComplete, onError)
            .also { subscriptions.add(it) }

    override fun <T> Maybe<T>.untilDestroy(
            onSuccess: (T) -> Unit,
            onError: (Throwable) -> Unit,
            onComplete: () -> Unit
    ): Disposable = observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError, onComplete)
            .also { subscriptions.add(it) }

}

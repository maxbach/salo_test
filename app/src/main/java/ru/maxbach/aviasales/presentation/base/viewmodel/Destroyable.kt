package ru.maxbach.aviasales.presentation.base.viewmodel

import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.functions.Functions
import timber.log.Timber

interface Destroyable {

    /**
     * Removes all subscriptions
     */
    fun clearSubscriptions()

    /**
     * Method should be used to guarantee that observable won't be subscribed after onDestroy.
     * It is automatically subscribing to the observable and calls onNextAction and onErrorAction on observable events.
     * Don't forget to process errors if observable can emit them.
     */
    fun <T> Flowable<T>.untilDestroy(
            onNext: (T) -> Unit = Functions.emptyConsumer<T>()::accept,
            onError: (Throwable) -> Unit = Timber::e,
            onComplete: () -> Unit = Functions.EMPTY_ACTION::run
    ): Disposable

    /**
     * Method should be used to guarantee that observable won't be subscribed after onDestroy.
     * It is automatically subscribing to the observable and calls onNextAction and onErrorAction on observable events.
     * Don't forget to process errors if observable can emit them.
     */
    fun <T> Observable<T>.untilDestroy(
            onNext: (T) -> Unit = Functions.emptyConsumer<T>()::accept,
            onError: (Throwable) -> Unit = Timber::e,
            onComplete: () -> Unit = Functions.EMPTY_ACTION::run
    ): Disposable

    /**
     * Method should be used to guarantee that single won't be subscribed after onDestroy.
     * It is automatically subscribing to the single and calls onSuccessAction and onErrorAction on single events.
     * Don't forget to process errors if single can emit them.
     */
    fun <T> Single<T>.untilDestroy(
            onSuccess: (T) -> Unit = Functions.emptyConsumer<T>()::accept,
            onError: (Throwable) -> Unit = Timber::e
    ): Disposable

    /**
     * Method should be used to guarantee that completable won't be subscribed after onDestroy.
     * It is automatically subscribing to the completable and calls onCompletedAction and onErrorAction on completable events.
     * Don't forget to process errors if completable can emit them.
     */
    fun Completable.untilDestroy(
            onComplete: () -> Unit = Functions.EMPTY_ACTION::run,
            onError: (Throwable) -> Unit = Timber::e
    ): Disposable

    /**
     * Method should be used to guarantee that maybe won't be subscribed after onDestroy.
     * It is automatically subscribing to the maybe and calls onSuccessAction and onErrorAction on maybe events.
     * Don't forget to process errors if completable can emit them.
     */
    fun <T> Maybe<T>.untilDestroy(
            onSuccess: (T) -> Unit = Functions.emptyConsumer<T>()::accept,
            onError: (Throwable) -> Unit = Timber::e,
            onComplete: () -> Unit = Functions.EMPTY_ACTION::run
    ): Disposable

}

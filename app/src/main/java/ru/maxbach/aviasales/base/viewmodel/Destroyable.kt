package ru.maxbach.aviasales.base.viewmodel

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
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
     *
     * @param onNext        Action which will raise on every [io.reactivex.Emitter.onNext] item;
     * @param onError       Action which will raise on every [io.reactivex.Emitter.onError] throwable;
     * @param onComplete    Action which will raise on every [io.reactivex.Emitter.onComplete] item;
     * @return [Disposable] which is wrapping source observable to unsubscribe from it onDestroy.
     */
    fun <T> Flowable<T>.untilDestroy(
            onNext: (T) -> Unit = Functions.emptyConsumer<T>()::accept,
            onError: (Throwable) -> Unit = Timber::wtf,
            onComplete: () -> Unit = Functions.EMPTY_ACTION::run
    ): Disposable

    /**
     * Method should be used to guarantee that observable won't be subscribed after onDestroy.
     * It is automatically subscribing to the observable and calls onNextAction and onErrorAction on observable events.
     * Don't forget to process errors if observable can emit them.
     *
     * @param onNext        Action which will raise on every [io.reactivex.Emitter.onNext] item;
     * @param onError       Action which will raise on every [io.reactivex.Emitter.onError] throwable;
     * @param onComplete    Action which will raise on every [io.reactivex.Emitter.onComplete] item;
     * @return [Disposable] which is wrapping source observable to unsubscribe from it onDestroy.
     */
    fun <T> Observable<T>.untilDestroy(
            onNext: (T) -> Unit = Functions.emptyConsumer<T>()::accept,
            onError: (Throwable) -> Unit = Timber::wtf,
            onComplete: () -> Unit = Functions.EMPTY_ACTION::run
    ): Disposable

    /**
     * Method should be used to guarantee that single won't be subscribed after onDestroy.
     * It is automatically subscribing to the single and calls onSuccessAction and onErrorAction on single events.
     * Don't forget to process errors if single can emit them.
     *
     * @param onSuccess     Action which will raise on every [io.reactivex.SingleEmitter.onSuccess] item;
     * @param onError       Action which will raise on every [io.reactivex.SingleEmitter.onError] throwable;
     * @return [Disposable] which is wrapping source single to unsubscribe from it onDestroy.
     */
    fun <T> Single<T>.untilDestroy(
            onSuccess: (T) -> Unit = Functions.emptyConsumer<T>()::accept,
            onError: (Throwable) -> Unit = Timber::wtf
    ): Disposable

    /**
     * Method should be used to guarantee that completable won't be subscribed after onDestroy.
     * It is automatically subscribing to the completable and calls onCompletedAction and onErrorAction on completable events.
     * Don't forget to process errors if completable can emit them.
     *
     * @param onComplete    Action which will raise on every [io.reactivex.CompletableEmitter.onComplete] item;
     * @param onError       Action which will raise on every [io.reactivex.CompletableEmitter.onError] throwable;
     * @return [Disposable] which is wrapping source completable to unsubscribe from it onDestroy.
     */
    fun Completable.untilDestroy(
            onComplete: () -> Unit = Functions.EMPTY_ACTION::run,
            onError: (Throwable) -> Unit = Timber::wtf
    ): Disposable

    /**
     * Method should be used to guarantee that maybe won't be subscribed after onDestroy.
     * It is automatically subscribing to the maybe and calls onSuccessAction and onErrorAction on maybe events.
     * Don't forget to process errors if completable can emit them.
     *
     * @param onSuccess     Action which will raise on every [io.reactivex.MaybeEmitter.onSuccess] ()} item;
     * @param onError       Action which will raise on every [io.reactivex.MaybeEmitter.onError] throwable;
     * @param onComplete    Action which will raise on every [io.reactivex.MaybeEmitter.onComplete] item;
     * @return [Disposable] which is wrapping source maybe to unsubscribe from it onDestroy.
     */
    fun <T> Maybe<T>.untilDestroy(
            onSuccess: (T) -> Unit = Functions.emptyConsumer<T>()::accept,
            onError: (Throwable) -> Unit = Timber::wtf,
            onComplete: () -> Unit = Functions.EMPTY_ACTION::run
    ): Disposable

}

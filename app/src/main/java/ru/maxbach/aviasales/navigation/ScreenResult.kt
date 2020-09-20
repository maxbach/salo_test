package ru.maxbach.aviasales.navigation

class ScreenResult<T> {

    private val observers = mutableListOf<(T) -> Unit>()

    var value: T? = null
        private set

    fun onNext(item: T) {
        value = item
        observers.forEach { it.invoke(item) }
    }

    fun observe(doOnNext: (T) -> Unit) {
        observers.add(doOnNext)
    }

}

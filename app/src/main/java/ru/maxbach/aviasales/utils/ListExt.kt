package ru.maxbach.aviasales.utils

fun <T> List<T>.addToBegin(item: T): List<T> = listOf(item) + this

fun <T> List<T>.addToBeginIfNotNull(item: T?): List<T> = listOfNotNull(item) + this

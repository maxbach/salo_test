package ru.maxbach.aviasales.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.toImmutable() = this as LiveData<T>

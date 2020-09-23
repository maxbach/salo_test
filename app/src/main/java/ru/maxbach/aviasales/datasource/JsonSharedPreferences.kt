package ru.maxbach.aviasales.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import com.squareup.moshi.Moshi
import javax.inject.Inject

class JsonSharedPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val moshi: Moshi
) {

    fun <T> getJsonObject(
        key: String,
        clazz: Class<T>
    ): T? = sharedPreferences.getString(key, null)?.let {
        val jsonAdapter = moshi.adapter(clazz)

        jsonAdapter.fromJson(it)
    }

    fun <T> getJsonObjectOrDefault(
        key: String,
        clazz: Class<T>,
        defaultValue: T
    ): T = getJsonObject(key, clazz) ?: defaultValue

    fun <T> writeJsonObject(
        key: String,
        clazz: Class<T>,
        newValue: T
    ) = sharedPreferences.edit(commit = true) {
        val jsonAdapter = moshi.adapter(clazz)

        putString(key, jsonAdapter.toJson(newValue))
    }

    fun <T> updateJsonObject(
        key: String,
        clazz: Class<T>,
        updateAction: (T?) -> T
    ) = writeJsonObject(
        key,
        clazz,
        updateAction(getJsonObject(key, clazz))
    )

}

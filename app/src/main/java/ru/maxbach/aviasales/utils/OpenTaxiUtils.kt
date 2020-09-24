package ru.maxbach.aviasales.utils

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import android.widget.Toast
import ru.maxbach.aviasales.R

fun Activity.openTaxiApp() {
    val supportedTaxiIntents = getSupportedTaxiIntents()

    when {
        supportedTaxiIntents.size > 1 -> {
            val chooserIntent = Intent.createChooser(
                supportedTaxiIntents.first(),
                getString(R.string.main_taxi_intent_chooser_title)
            )
            chooserIntent.putExtra(
                Intent.EXTRA_INITIAL_INTENTS,
                supportedTaxiIntents.drop(1).toTypedArray<Parcelable>()
            )
            startActivity(chooserIntent)
        }
        supportedTaxiIntents.size == 1 -> {
            startActivity(supportedTaxiIntents[0])
        }
        else -> {
            Toast.makeText(
                this,
                getString(R.string.main_taxi_intent_chooser_title),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

fun Activity.getSupportedTaxiIntents(): List<Intent> = listOf(
    "ru.yandex.taxi",
    "com.ubercab",
    "ru.yandex.uber",
    "me.lyft.android",
    "com.gettaxi.android",
    "com.citymobil",
    "com.taxsee.taxsee"
)
    .map { Intent().setPackage(it) }
    .filter { it.resolveActivity(packageManager) != null }

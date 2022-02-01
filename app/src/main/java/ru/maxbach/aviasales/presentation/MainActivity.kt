package ru.maxbach.aviasales.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.maxbach.aviasales.App
import ru.maxbach.aviasales.R
import ru.maxbach.aviasales.presentation.navigation.AnimatedNavigator
import ru.maxbach.aviasales.presentation.navigation.Screens
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.touchin.roboswag.navigation_cicerone.CiceroneTuner
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDi()
        setupNavigation()

        if (savedInstanceState == null) {
            router.newRootScreen(Screens.Main())
        }
    }

    private fun setupNavigation() {
        lifecycle.addObserver(
            CiceroneTuner(
                navigatorHolder,
                AnimatedNavigator(this, supportFragmentManager, R.id.fragment_host)
            )
        )
    }

    private fun injectDi() {
        (application as App)
            .component
            .inject(this)
    }
}

package ru.maxbach.aviasales

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.maxbach.aviasales.navigation.Screens
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
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
    }

    private fun setupNavigation() {
        lifecycle.addObserver(
                CiceroneTuner(
                        navigatorHolder,
                        SupportAppNavigator(this, R.id.fragment_host)
                )
        )

        router.newRootScreen(Screens.Main())
    }

    private fun injectDi() {
        (application as App)
                .component
                .inject(this)
    }
}

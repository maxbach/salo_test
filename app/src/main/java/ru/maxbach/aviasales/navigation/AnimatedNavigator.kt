package ru.maxbach.aviasales.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import org.jetbrains.annotations.NotNull
import ru.maxbach.aviasales.R
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command

class AnimatedNavigator(
    activity: @NotNull FragmentActivity,
    fragmentManager: @NotNull FragmentManager,
    containerId: Int
) : SupportAppNavigator(activity, fragmentManager, containerId) {

    override fun setupFragmentTransaction(
        command: Command,
        currentFragment: Fragment?,
        nextFragment: Fragment?,
        fragmentTransaction: FragmentTransaction
    ) {
        super.setupFragmentTransaction(command, currentFragment, nextFragment, fragmentTransaction)

        fragmentTransaction.setCustomAnimations(
            R.anim.fragment_fade_enter,
            R.anim.fragment_close_exit,
            R.anim.fragment_open_enter,
            R.anim.fragment_fade_exit
        )
    }
}

package ru.maxbach.aviasales.base.fragment

import android.os.Parcelable
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.maxbach.aviasales.App
import ru.maxbach.aviasales.base.viewmodel.BaseViewModel
import ru.maxbach.aviasales.di.viewmodel.ViewModelFactory

@Suppress("UNCHECKED_CAST")
open class BaseFragment<NavArgs : Parcelable, VM : BaseViewModel<*, NavArgs>>(
    @LayoutRes layoutId: Int
) : Fragment(layoutId) {

    companion object {
        const val NAV_ARGS_KEY = "nav_args"
    }

    fun putNavArgs(navArgs: NavArgs = EmptyState as NavArgs) {
        arguments = bundleOf(NAV_ARGS_KEY to navArgs)
    }

    @MainThread
    protected inline fun <reified ViewModel : VM> viewModel(): Lazy<ViewModel> =
        lazy {
            val fragmentArguments = arguments ?: bundleOf()

            ViewModelProvider(
                viewModelStore,
                ViewModelFactory(
                    viewModelMap = (requireActivity().application as App).viewModelMap,
                    owner = this,
                    arguments = fragmentArguments
                )
            ).get(ViewModel::class.java)
        }
}

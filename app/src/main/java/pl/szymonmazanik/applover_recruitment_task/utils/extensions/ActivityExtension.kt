package pl.szymonmazanik.applover_recruitment_task.utils.extensions

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pl.szymonmazanik.applover_recruitment_task.utils.factory.ViewModelFactory

/**
 * Gets view model within activity easily
 */
inline fun <reified T : ViewModel> FragmentActivity.getViewModel(noinline creator: (() -> T)? = null): T {
    return if (creator == null)
        ViewModelProvider(this).get(T::class.java)
    else
        ViewModelProvider(this, ViewModelFactory(creator)).get(T::class.java)
}
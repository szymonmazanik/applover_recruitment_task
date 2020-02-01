package pl.szymonmazanik.applover_recruitment_task.utils.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Factory class for [ViewModel]
 */
class ViewModelFactory<T>(val creator: () -> T) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = creator() as T
}
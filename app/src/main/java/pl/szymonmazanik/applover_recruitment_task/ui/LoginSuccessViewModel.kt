package pl.szymonmazanik.applover_recruitment_task.ui

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Empty view model for future use
 */
class LoginSuccessViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
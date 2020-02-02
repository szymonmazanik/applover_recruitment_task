package pl.szymonmazanik.applover_recruitment_task.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import pl.szymonmazanik.applover_recruitment_task.R
import pl.szymonmazanik.applover_recruitment_task.model.entity.request.LoginRequest
import pl.szymonmazanik.applover_recruitment_task.model.helper.ApiHelper
import pl.szymonmazanik.applover_recruitment_task.utils.Event
import pl.szymonmazanik.applover_recruitment_task.utils.extensions.isInputDataValid
import java.util.concurrent.TimeUnit


class LoginFormViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    //Could be injected with dagger but no time left
    private val apiHelper by lazy {
        ApiHelper(
            compositeDisposable,
            _errorMessage,
            _isLoading
        )
    }

    private val backButtonClickSource = PublishSubject.create<Boolean>()

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    private val _errorMessage = MutableLiveData<Event<Int?>>()
    val errorMessage: LiveData<Event<Int?>> = _errorMessage

    private val _loginSuccess = MutableLiveData<Event<Boolean>>()
    val loginSuccess: LiveData<Event<Boolean>> = _loginSuccess

    private val _onExitAppEvent = MutableLiveData<Event<Boolean>>()
    val onExitAppEvent: LiveData<Event<Boolean>> = _onExitAppEvent

    // Two-way data binding, exposing MutableLiveData
    val loginRequest = MutableLiveData(LoginRequest("login@applover.pl", "password123"))

    init {
        observeBackButton()
    }

    /**
     * Validate input data and execute [ApiHelper] login
     */
    fun login() =
        loginRequest.value?.let {
            if (it.isInputDataValid()) {
                apiHelper.login(it, _loginSuccess)
            } else postErrorMessage(R.string.invalid_data)
        } ?: postErrorMessage(R.string.null_data)

    fun onBackPressed() = backButtonClickSource.onNext(true)

    private fun observeBackButton() {
        compositeDisposable += backButtonClickSource
            .debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .timeInterval(TimeUnit.MILLISECONDS)
            .skip(1)
            .filter { interval -> interval.time() < EXIT_TIMEOUT }
            .subscribeBy(onNext = {
                _onExitAppEvent.postValue(Event(true))
            })
    }

    private fun postErrorMessage(id: Int?) {
        _errorMessage.postValue(Event(id))
    }

    /**
     * Clears [CompositeDisposable]
     */
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    companion object {
        private const val EXIT_TIMEOUT: Long = 2000
        private const val DEBOUNCE_TIMEOUT: Long = 100
    }
}
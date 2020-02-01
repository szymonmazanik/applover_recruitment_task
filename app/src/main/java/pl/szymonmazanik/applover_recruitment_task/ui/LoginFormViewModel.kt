package pl.szymonmazanik.applover_recruitment_task.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import pl.szymonmazanik.applover_recruitment_task.BuildConfig
import pl.szymonmazanik.applover_recruitment_task.R
import pl.szymonmazanik.applover_recruitment_task.model.entity.request.LoginRequest
import pl.szymonmazanik.applover_recruitment_task.model.helper.ApiHelper
import pl.szymonmazanik.applover_recruitment_task.utils.Event
import pl.szymonmazanik.applover_recruitment_task.utils.extensions.isValidEmail
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

//    private val _onBackPressedEvent = MutableLiveData<Event<Boolean>>()
//    val onBackPressedEvent: LiveData<Event<Boolean>> = _onBackPressedEvent

    private val _onExitAppEvent = MutableLiveData<Event<Boolean>>()
    val onExitAppEvent: LiveData<Event<Boolean>> = _onExitAppEvent

    // Two-way data binding, exposing MutableLiveData
    val email = MutableLiveData<String>().apply { if(BuildConfig.DEBUG) this.value = "login@applover.pl" }
    val password = MutableLiveData<String>().apply { if(BuildConfig.DEBUG) this.value = "password123" }

    init {
        observeBackButton()
    }

    private fun observeBackButton() {
        compositeDisposable += backButtonClickSource
            .debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
//            .doOnNext{_onBackPressedEvent.postValue(Event(true))}
//            .doOnNext(__ -> Toast.makeText(this, "Press once again to exit", Toast.LENGTH_SHORT).show())
            .timeInterval(TimeUnit.MILLISECONDS)
            .skip(1)
            .filter { interval -> interval.time() < EXIT_TIMEOUT }
            .subscribeBy ( onNext = {
                _onExitAppEvent.postValue(Event(true))
            })
    }

    /**
     * Validate input data and execute [ApiHelper] login
     */
    fun login() =
        email.value?.let { email ->
            if (email.isValidEmail()) {
                password.value?.let { password ->
                    if(password.isNotEmpty()) {
                        val loginRequest = LoginRequest(email, password)
                        apiHelper.login(loginRequest, _loginSuccess)
                    } else _errorMessage.postValue(Event(R.string.empty_password))
                } ?: _errorMessage.postValue(Event(R.string.null_password))
            } else _errorMessage.postValue(Event(R.string.invalid_email))
        } ?: _errorMessage.postValue(Event(R.string.null_email))

    fun onBackPressed() = backButtonClickSource.onNext(true)

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
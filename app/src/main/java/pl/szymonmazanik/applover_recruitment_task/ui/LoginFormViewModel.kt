package pl.szymonmazanik.applover_recruitment_task.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import pl.szymonmazanik.applover_recruitment_task.model.entity.request.LoginRequest
import pl.szymonmazanik.applover_recruitment_task.model.helper.ApiHelper
import pl.szymonmazanik.applover_recruitment_task.utils.Event
import pl.szymonmazanik.applover_recruitment_task.utils.extensions.isValidEmail


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

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    private val _errorMessage = MutableLiveData<Event<Int?>>()
    val errorMessage: LiveData<Event<Int?>> = _errorMessage

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    // Two-way data binding, exposing MutableLiveData
    val email = MutableLiveData<String>().apply { this.value = "login@applover.pl" }
    val password = MutableLiveData<String>().apply { this.value = "password123" }

    /**
     * Validate input data and execute [ApiHelper] login
     */
    fun login() =
        email.value?.let { email ->
            if (email.isValidEmail()) {
                password.value?.let { password ->
                    val loginRequest = LoginRequest(email, password)
                    apiHelper.login(loginRequest, _loginSuccess)
                } ?: _errorMessage.postValue(Event(0))
            } else _errorMessage.postValue(Event(0))
        } ?: _errorMessage.postValue(Event(0))


    /**
     * Clears [CompositeDisposable]
     */
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
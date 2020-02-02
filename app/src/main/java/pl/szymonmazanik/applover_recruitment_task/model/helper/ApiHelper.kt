package pl.szymonmazanik.applover_recruitment_task.model.helper

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import pl.szymonmazanik.applover_recruitment_task.R
import pl.szymonmazanik.applover_recruitment_task.model.entity.request.LoginRequest
import pl.szymonmazanik.applover_recruitment_task.model.network.AppLoverApi
import pl.szymonmazanik.applover_recruitment_task.utils.Event
import retrofit2.HttpException
import timber.log.Timber

class ApiHelper(
    private val compositeDisposable: CompositeDisposable,
    private val errorMessage: MutableLiveData<Event<Int?>>,
    private val isLoading: MutableLiveData<Event<Boolean>>
) {
    private val api = AppLoverApi.getService()

    /**
     * Execute login request by adding it to [CompositeDisposable]
     * @param loginRequest holds login and password in [LoginRequest] data class
     * @param loginSuccess is a [MutableLiveData] trigger for UI layer
     */
    fun login(loginRequest: LoginRequest, loginSuccess: MutableLiveData<Event<Boolean>>) {
        compositeDisposable += api.login(loginRequest)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { isLoading.postValue(Event(true)) }
            .doFinally { isLoading.postValue(Event(false)) }
            .subscribeBy(
                onSuccess = {
                    loginSuccess.postValue(Event(true))
                    Timber.d("Login success. Token: ${it.token}")
                },
                onError = {
                    handleError(it)
                    Timber.e("Login failed. Error: $it")
                }
            )
    }

    /**
     * Posts custom error messages to [errorMessage] for given error
     * @param error thrown by API
     */
    private fun handleError(error: Throwable) {
        if (error is HttpException) {
            when (error.code()) {
                UNAUTHORIZED ->
                    postErrorMessage(R.string.bad_login_or_password)
                INTERNAL_SERVER_ERROR ->
                    postErrorMessage(R.string.internal_server_error)
                else -> postErrorMessage(R.string.unknown_error)
            }
        } else {
            postErrorMessage(R.string.unknown_error)
        }
    }

    private fun postErrorMessage(id: Int?) = errorMessage.postValue(Event(id))

    companion object {
        //Http errors
        private const val UNAUTHORIZED = 401
        private const val INTERNAL_SERVER_ERROR = 500
    }
}
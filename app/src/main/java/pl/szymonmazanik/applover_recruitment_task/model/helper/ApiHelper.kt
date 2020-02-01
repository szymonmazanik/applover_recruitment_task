package pl.szymonmazanik.applover_recruitment_task.model.helper

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import pl.szymonmazanik.applover_recruitment_task.model.entity.request.LoginRequest
import pl.szymonmazanik.applover_recruitment_task.model.network.AppLoverApi
import pl.szymonmazanik.applover_recruitment_task.utils.Event

class ApiHelper(
    private val compositeDisposable: CompositeDisposable,
    private val errorMessage: MutableLiveData<Event<Int?>>,
    private val isLoading: MutableLiveData<Event<Boolean>>
) {
    private val api = AppLoverApi.getService()

    fun login(loginRequest: LoginRequest, loginSuccess: MutableLiveData<Boolean>) {
        compositeDisposable += api.login(loginRequest)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { isLoading.postValue(Event(true)) }
            .doFinally { isLoading.postValue(Event(false)) }
            .subscribeBy(
                onSuccess = {
                    loginSuccess.postValue(true)
                },
                onError = {
                    errorMessage.postValue(Event(0))
                }
            )
    }
}
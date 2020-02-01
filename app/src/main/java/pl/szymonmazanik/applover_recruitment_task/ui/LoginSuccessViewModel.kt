package pl.szymonmazanik.applover_recruitment_task.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.operators.flowable.FlowableBlockingSubscribe.subscribe
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import pl.szymonmazanik.applover_recruitment_task.utils.Event
import java.util.concurrent.TimeUnit


class LoginSuccessViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()




    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
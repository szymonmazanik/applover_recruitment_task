package pl.szymonmazanik.applover_recruitment_task

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.szymonmazanik.applover_recruitment_task.model.entity.request.LoginRequest
import pl.szymonmazanik.applover_recruitment_task.ui.LoginFormViewModel
import java.util.concurrent.Callable


class LoginFormViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    private lateinit var viewModel: LoginFormViewModel

    private val loginRequest = LoginRequest("login@applover.pl", "password123")

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        viewModel = LoginFormViewModel()
        viewModel.loginRequest.postValue(loginRequest)
    }

    @Test
    fun testLogin() {
        viewModel.loginSuccess.test().awaitValue().assertHasValue().assertValue {
            it.getContentIfNotHandled()?.equals(true)
        }
        viewModel.login()
    }




}
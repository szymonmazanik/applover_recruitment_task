package pl.szymonmazanik.applover_recruitment_task.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login_form.*
import pl.szymonmazanik.applover_recruitment_task.R
import pl.szymonmazanik.applover_recruitment_task.databinding.FragmentLoginFormBinding
import pl.szymonmazanik.applover_recruitment_task.utils.EventObserver
import pl.szymonmazanik.applover_recruitment_task.utils.extensions.getViewModel
import timber.log.Timber

class LoginFormFragment : Fragment() {
    private val viewModel by lazy {
        activity?.run {
            getViewModel { LoginFormViewModel() }
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<FragmentLoginFormBinding>(
            inflater,
            R.layout.fragment_login_form,
            container,
            false
        ).apply {
            this.viewModel = this@LoginFormFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupNavigation()
        setupErrorSnackbar()
        setupProgressBar()

    }

    private fun setupNavigation() {
        viewModel.loginSuccess.observe(this, Observer {
            findNavController().navigate(R.id.action_form_to_success)
        })
    }

    private fun setupErrorSnackbar() {
        viewModel.errorMessage.observe(this, EventObserver { resId ->
            resId?.let {
                view?.let { view ->
                   // Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
                    Timber.d("asdf $it")
                }
            }
        })
    }

    private fun setupProgressBar() {
        viewModel.isLoading.observe(this, EventObserver {
            if (it) {
                login_button.visibility = View.GONE
                progress_circular.visibility = View.VISIBLE
            } else {
                progress_circular.visibility = View.GONE
                login_button.visibility = View.VISIBLE
            }
        })
    }
}

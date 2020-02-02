package pl.szymonmazanik.applover_recruitment_task.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pl.szymonmazanik.applover_recruitment_task.R
import pl.szymonmazanik.applover_recruitment_task.databinding.FragmentLoginSuccessBinding
import pl.szymonmazanik.applover_recruitment_task.utils.extensions.getViewModel
import timber.log.Timber

class LoginSuccessFragment : Fragment() {
    private val viewModel by lazy {
        activity?.run {
            getViewModel { LoginSuccessViewModel() }
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<FragmentLoginSuccessBinding>(
            inflater,
            R.layout.fragment_login_success,
            container,
            false
        ).apply {
            this.viewModel = this@LoginSuccessFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupNavigation()
    }

    private fun setupNavigation() =
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_success_to_form)
            Timber.d("Back button pressed on Success fragment")
        }
}
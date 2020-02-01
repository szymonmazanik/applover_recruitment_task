package pl.szymonmazanik.applover_recruitment_task.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import pl.szymonmazanik.applover_recruitment_task.R
import pl.szymonmazanik.applover_recruitment_task.databinding.FragmentLoginFormBinding
import pl.szymonmazanik.applover_recruitment_task.databinding.FragmentLoginSuccessBinding
import pl.szymonmazanik.applover_recruitment_task.utils.extensions.getViewModel

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
}
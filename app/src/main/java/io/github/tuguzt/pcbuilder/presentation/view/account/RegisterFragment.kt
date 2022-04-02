package io.github.tuguzt.pcbuilder.presentation.view.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.haroldadmin.cnradapter.NetworkResponse
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.databinding.FragmentRegisterBinding
import io.github.tuguzt.pcbuilder.domain.interactor.checkPassword
import io.github.tuguzt.pcbuilder.domain.interactor.checkUsername
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import io.github.tuguzt.pcbuilder.presentation.model.user.UserNamePasswordData
import io.github.tuguzt.pcbuilder.presentation.view.snackbarShort
import io.github.tuguzt.pcbuilder.presentation.viewmodel.account.AuthViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RegisterFragment : Fragment() {
    companion object {
        @JvmStatic
        private val LOG_TAG = RegisterFragment::class.simpleName
    }

    private val authViewModel: AuthViewModel by sharedViewModel()

    private var _binding: FragmentRegisterBinding? = null

    // This helper property is only valid between `onCreateView` and `onDestroyView`.
    private inline val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentRegisterBinding.inflate(inflater, container, false)
        .also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.run {
            login.setOnClickListener {
                val action = RegisterFragmentDirections.actionLoginFragment()
                findNavController().navigate(action)
            }

            @Suppress("NAME_SHADOWING")
            register.setOnClickListener {
                val username = username.text.toString()
                val password = password.text.toString()
                if (username.isNotBlank() && password.isNotBlank()) {
                    val username = username.trim()
                    val password = password.trim()
                    if (checkUsername(username) && checkPassword(password)) {
                        val user = UserNamePasswordData(
                            email = null,
                            imageUri = null,
                            role = UserRole.User,
                            username = username,
                            password = password,
                        )
                        lifecycleScope.launch {
                            when (val result =
                                authViewModel.register(requireActivity().application, user)) {
                                is NetworkResponse.Success -> {
                                    with(requireActivity()) {
                                        setResult(AppCompatActivity.RESULT_OK)
                                        finish()
                                    }
                                }
                                is NetworkResponse.ServerError -> {
                                    Log.e(LOG_TAG, "Server error", result.error)
                                    snackbarShort { getString(R.string.server_error) }.show()
                                }
                                is NetworkResponse.NetworkError -> {
                                    Log.e(LOG_TAG, "Network error", result.error)
                                    snackbarShort { getString(R.string.network_error) }.show()
                                }
                                is NetworkResponse.UnknownError -> {
                                    Log.e(LOG_TAG, "Application error", result.error)
                                    snackbarShort { getString(R.string.application_error) }.show()
                                }
                            }
                        }
                        return@setOnClickListener
                    }
                    snackbarShort(root) { getString(R.string.incorrect_input_username_password) }.show()
                    return@setOnClickListener
                }
                snackbarShort(root) { getString(R.string.username_password_empty) }.show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

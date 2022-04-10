package io.github.tuguzt.pcbuilder.presentation.view.account

import android.os.Bundle
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
import io.github.tuguzt.pcbuilder.presentation.view.showSnackbar
import io.github.tuguzt.pcbuilder.presentation.viewmodel.account.AuthViewModel
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RegisterFragment : Fragment() {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private val authViewModel: AuthViewModel by sharedViewModel()

    private var _binding: FragmentRegisterBinding? = null

    // This helper property is only valid between `onCreateView` and `onDestroyView`.
    private inline val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.run {
            login.setOnClickListener {
                findNavController().popBackStack()
            }

            @Suppress("NAME_SHADOWING")
            register.setOnClickListener {
                val username = username.text?.toString().orEmpty()
                val password = password.text?.toString().orEmpty()
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
                                    logger.error(result.error) { "Server error" }
                                    showSnackbar(root, R.string.server_error)
                                }
                                is NetworkResponse.NetworkError -> {
                                    logger.error(result.error) { "Network error" }
                                    showSnackbar(root, R.string.network_error)
                                }
                                is NetworkResponse.UnknownError -> {
                                    logger.error(result.error) { "Application error" }
                                    showSnackbar(root, R.string.application_error)
                                }
                            }
                        }
                        return@setOnClickListener
                    }
                    showSnackbar(root, R.string.incorrect_input_username_password)
                    return@setOnClickListener
                }
                showSnackbar(root, R.string.username_password_empty)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

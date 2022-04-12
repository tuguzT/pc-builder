package io.github.tuguzt.pcbuilder.presentation.view.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.haroldadmin.cnradapter.NetworkResponse
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.databinding.FragmentLoginBinding
import io.github.tuguzt.pcbuilder.domain.interactor.checkPassword
import io.github.tuguzt.pcbuilder.domain.interactor.checkUsername
import io.github.tuguzt.pcbuilder.presentation.model.user.UserCredentialsData
import io.github.tuguzt.pcbuilder.presentation.model.user.toIntent
import io.github.tuguzt.pcbuilder.presentation.view.showSnackbar
import io.github.tuguzt.pcbuilder.presentation.viewmodel.account.AuthViewModel
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment : Fragment() {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private val authViewModel: AuthViewModel by sharedViewModel()
    private val googleSignInClient: GoogleSignInClient by inject()

    private var _binding: FragmentLoginBinding? = null

    // This helper property is only valid between `onCreateView` and `onDestroyView`.
    private inline val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = requireActivity() as AuthActivity

        val contract = ActivityResultContracts.StartActivityForResult()
        val googleSignInLauncher = registerForActivityResult(contract) {
            if (it.resultCode != AppCompatActivity.RESULT_OK) {
                showSnackbar(binding.root, R.string.user_not_signed_in)
                return@registerForActivityResult
            }
            lifecycleScope.launch {
                val data = it.data
                val result = runCatching {
                    authViewModel.googleOAuth2(activity.application, data)
                }
                when (val response = result.getOrNull()) {
                    null -> {
                        logger.error(result.exceptionOrNull()) { "Google authorization failed" }
                        showSnackbar(binding.root, R.string.google_auth_failed)
                    }
                    else -> when (response) {
                        is NetworkResponse.Success -> {
                            with(activity) {
                                val intent = response.body.toIntent()
                                setResult(AppCompatActivity.RESULT_OK, intent)
                                finish()
                            }
                        }
                        is NetworkResponse.ServerError -> {
                            logger.error(response.error) { "Server error" }
                            showSnackbar(binding.root, R.string.server_error)
                        }
                        is NetworkResponse.NetworkError -> {
                            logger.error(response.error) { "Network error" }
                            showSnackbar(binding.root, R.string.network_error)
                        }
                        is NetworkResponse.UnknownError -> {
                            logger.error(response.error) { "Application error" }
                            showSnackbar(binding.root, R.string.application_error)
                        }
                    }
                }
            }
        }

        binding.run {
            googleButton.setOnClickListener {
                val signInIntent = googleSignInClient.signInIntent
                googleSignInLauncher.launch(signInIntent)
            }

            register.setOnClickListener {
                val action = LoginFragmentDirections.actionRegisterFragment()
                findNavController().navigate(action)
            }

            @Suppress("NAME_SHADOWING")
            signIn.setOnClickListener {
                val username = username.text.toString()
                val password = password.text.toString()
                if (username.isNotBlank() && password.isNotBlank()) {
                    val username = username.trim()
                    val password = password.trim()
                    if (checkUsername(username) && checkPassword(password)) {
                        val credentials = UserCredentialsData(username, password)
                        lifecycleScope.launch {
                            val application = activity.application
                            when (val response = authViewModel.auth(application, credentials)) {
                                is NetworkResponse.Success -> {
                                    with(activity) {
                                        setResult(AppCompatActivity.RESULT_OK)
                                        finish()
                                    }
                                }
                                is NetworkResponse.ServerError -> {
                                    logger.error(response.error) { "Server error" }
                                    showSnackbar(root, R.string.server_error)
                                }
                                is NetworkResponse.NetworkError -> {
                                    logger.error(response.error) { "Network error" }
                                    showSnackbar(root, R.string.network_error)
                                }
                                is NetworkResponse.UnknownError -> {
                                    logger.error(response.error) { "Application error" }
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

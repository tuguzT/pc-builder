package io.github.tuguzt.pcbuilder.presentation.view.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.haroldadmin.cnradapter.NetworkResponse
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.databinding.FragmentLoginBinding
import io.github.tuguzt.pcbuilder.domain.interactor.checkPassword
import io.github.tuguzt.pcbuilder.domain.interactor.checkUsername
import io.github.tuguzt.pcbuilder.presentation.model.user.UserCredentialsData
import io.github.tuguzt.pcbuilder.presentation.model.user.toIntent
import io.github.tuguzt.pcbuilder.presentation.model.user.toUser
import io.github.tuguzt.pcbuilder.presentation.view.googleSignInOptions
import io.github.tuguzt.pcbuilder.presentation.view.showSnackbar
import io.github.tuguzt.pcbuilder.presentation.view.userSharedPreferences
import io.github.tuguzt.pcbuilder.presentation.viewmodel.account.AuthViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment : Fragment() {
    companion object {
        @JvmStatic
        private val LOG_TAG = AuthActivity::class.simpleName
    }

    private val authViewModel: AuthViewModel by sharedViewModel()

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
        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)

        val contract = ActivityResultContracts.StartActivityForResult()
        val googleSignInLauncher = registerForActivityResult(contract) {
            if (it.resultCode != AppCompatActivity.RESULT_OK) {
                showSnackbar(binding.root, R.string.user_not_signed_in)
                return@registerForActivityResult
            }
            lifecycleScope.launch {
                try {
                    val data = it.data
                    val googleAccount = GoogleSignIn.getSignedInAccountFromIntent(data).await()

                    val sharedPreferences = requireActivity().application.userSharedPreferences
                    sharedPreferences.edit {
                        putString("google_token", googleAccount.idToken)
                        putString("google_username", googleAccount.displayName)
                    }
                    val user = googleAccount.toUser()
                    with(requireActivity()) {
                        setResult(AppCompatActivity.RESULT_OK, user.toIntent())
                        finish()
                    }
                } catch (exception: ApiException) {
                    Log.e(LOG_TAG, "Google authorization failed", exception)
                    showSnackbar(binding.root, R.string.google_auth_failed)
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
                            when (val result =
                                authViewModel.auth(requireActivity().application, credentials)) {
                                is NetworkResponse.Success -> {
                                    with(requireActivity()) {
                                        setResult(AppCompatActivity.RESULT_OK)
                                        finish()
                                    }
                                }
                                is NetworkResponse.ServerError -> {
                                    Log.e(LOG_TAG, "Server error", result.error)
                                    showSnackbar(root, R.string.server_error)
                                }
                                is NetworkResponse.NetworkError -> {
                                    Log.e(LOG_TAG, "Network error", result.error)
                                    showSnackbar(root, R.string.network_error)
                                }
                                is NetworkResponse.UnknownError -> {
                                    Log.e(LOG_TAG, "Application error", result.error)
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

package io.github.tuguzt.pcbuilder.presentation.viewmodel.root.auth

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.pcbuilder.data.datasource.remote.BackendResponse
import io.github.tuguzt.pcbuilder.data.repository.AuthRepository
import io.github.tuguzt.pcbuilder.data.repository.CurrentUserRepository
import io.github.tuguzt.pcbuilder.data.repository.UserTokenRepository
import io.github.tuguzt.pcbuilder.data.repository.UsersRepository
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserCredentialsData
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserTokenData
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.viewmodel.UserMessage
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import mu.KotlinLogging
import javax.inject.Inject

/**
 * Injectable view model for user authentication.
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val usersRepository: UsersRepository,
    private val tokenRepository: UserTokenRepository,
    private val googleSignInClient: GoogleSignInClient,
    private val currentUserRepository: CurrentUserRepository,
) : ViewModel() {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private var _uiState by mutableStateOf(AuthState())
    val uiState get() = _uiState

    fun updateUsername(username: String) {
        _uiState = uiState.copy(username = username)
    }

    fun updatePassword(password: String) {
        _uiState = uiState.copy(password = password)
    }

    fun updatePasswordVisible(passwordVisible: Boolean) {
        _uiState = uiState.copy(passwordVisible = passwordVisible)
    }

    fun updateIsLoggedIn(isLoggedIn: Boolean) {
        _uiState = uiState.copy(isLoggedIn = isLoggedIn)
    }

    fun userMessageShown(messageId: NanoId) {
        val messages = uiState.userMessages.filterNot { it.id == messageId }
        _uiState = uiState.copy(userMessages = messages)
    }

    private var authJob: Job? = null

    fun auth(context: Context) {
        authJob?.cancel()
        authJob = viewModelScope.launch {
            _uiState = uiState.copy(isLoading = true)
            val credentials = UserCredentialsData(
                username = uiState.username,
                password = uiState.password,
            )
            authRepository.auth(credentials).handle(context) { tokenData ->
                tokenRepository.setToken(tokenData)
                usersRepository.current().handle(context) {
                    currentUserRepository.updateCurrentUser(it)
                    _uiState = uiState.copy(isLoggedIn = true)
                }
            }
            _uiState = uiState.copy(isLoading = false)
        }
    }

    fun register(context: Context) {
        authJob?.cancel()
        authJob = viewModelScope.launch {
            _uiState = uiState.copy(isLoading = true)
            val credentials = UserCredentialsData(
                username = uiState.username,
                password = uiState.password,
            )
            authRepository.register(credentials).handle(context) { token ->
                tokenRepository.setToken(token)
                usersRepository.current().handle(context) {
                    currentUserRepository.updateCurrentUser(it)
                    _uiState = uiState.copy(isLoggedIn = true)
                }
            }
            _uiState = uiState.copy(isLoading = false)
        }
    }

    val googleSignInIntent: Intent
        get() = googleSignInClient.signInIntent

    fun googleOAuth2(account: GoogleSignInAccount, context: Context) {
        val authCodeString = account.serverAuthCode ?: run {
            // todo get message from string resources
            val newMessage = UserMessage("Cannot retrieve id from Google account")
            val errorMessages = uiState.userMessages + newMessage
            _uiState = uiState.copy(userMessages = errorMessages)
            return
        }

        authJob?.cancel()
        authJob = viewModelScope.launch {
            _uiState = uiState.copy(isLoading = true)
            val authCode = UserTokenData(authCodeString)
            authRepository.googleOAuth2(authCode).handle(context) { token ->
                tokenRepository.setToken(token)
                usersRepository.current().handle(context) {
                    currentUserRepository.updateCurrentUser(it)
                    _uiState = uiState.copy(isLoggedIn = true)
                }
            }
            _uiState = uiState.copy(isLoading = false)
        }
    }

    private inline fun <S> BackendResponse<S>.handle(
        context: Context,
        serverErrorMessage: String = context.getString(R.string.server_error),
        networkErrorMessage: String = context.getString(R.string.network_error),
        unknownErrorMessage: String = context.getString(R.string.unknown_error),
        onSuccess: (S) -> Unit,
    ): Unit = when (this) {
        is NetworkResponse.Success -> onSuccess(body)
        is NetworkResponse.ServerError -> {
            logger.error(error) { "Server error occurred" }
            val errorMessages = uiState.userMessages + UserMessage(serverErrorMessage)
            _uiState = uiState.copy(userMessages = errorMessages)
        }
        is NetworkResponse.NetworkError -> {
            logger.error(error) { "Network error occurred" }
            val errorMessages = uiState.userMessages + UserMessage(networkErrorMessage)
            _uiState = uiState.copy(userMessages = errorMessages)
        }
        is NetworkResponse.UnknownError -> {
            logger.error(error) { "Unknown error occurred" }
            val errorMessages = uiState.userMessages + UserMessage(unknownErrorMessage)
            _uiState = uiState.copy(userMessages = errorMessages)
        }
    }
}

package io.github.tuguzt.pcbuilder.presentation.viewmodel.root.auth

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

    fun auth() {
        authJob?.cancel()
        authJob = viewModelScope.launch {
            _uiState = uiState.copy(isLoading = true)
            val credentials = UserCredentialsData(
                username = uiState.username,
                password = uiState.password,
            )
            authRepository.auth(credentials).handle { tokenData ->
                tokenRepository.setToken(tokenData)
                usersRepository.current().handle {
                    currentUserRepository.updateCurrentUser(it)
                    _uiState = uiState.copy(isLoggedIn = true)
                }
            }
            _uiState = uiState.copy(isLoading = false)
        }
    }

    fun register() {
        authJob?.cancel()
        authJob = viewModelScope.launch {
            _uiState = uiState.copy(isLoading = true)
            val credentials = UserCredentialsData(
                username = uiState.username,
                password = uiState.password,
            )
            authRepository.register(credentials).handle { token ->
                tokenRepository.setToken(token)
                usersRepository.current().handle {
                    currentUserRepository.updateCurrentUser(it)
                    _uiState = uiState.copy(isLoggedIn = true)
                }
            }
            _uiState = uiState.copy(isLoading = false)
        }
    }

    val googleSignInIntent: Intent
        get() = googleSignInClient.signInIntent

    fun googleOAuth2(account: GoogleSignInAccount) {
        val authCodeString = account.serverAuthCode ?: run {
            val newMessage = UserMessage(AuthMessageKind.NoGoogleId)
            val errorMessages = uiState.userMessages + newMessage
            _uiState = uiState.copy(userMessages = errorMessages)
            return
        }

        authJob?.cancel()
        authJob = viewModelScope.launch {
            _uiState = uiState.copy(isLoading = true)
            val authCode = UserTokenData(authCodeString)
            authRepository.googleOAuth2(authCode).handle { token ->
                tokenRepository.setToken(token)
                usersRepository.current().handle {
                    currentUserRepository.updateCurrentUser(it)
                    _uiState = uiState.copy(isLoggedIn = true)
                }
            }
            _uiState = uiState.copy(isLoading = false)
        }
    }

    private inline fun <S> BackendResponse<S>.handle(onSuccess: (S) -> Unit): Unit = when (this) {
        is NetworkResponse.Success -> onSuccess(body)
        is NetworkResponse.ServerError -> {
            logger.error(error) { "Server error occurred" }
            val message = UserMessage(AuthMessageKind.Backend.server())
            val errorMessages = uiState.userMessages + message
            _uiState = uiState.copy(userMessages = errorMessages)
        }
        is NetworkResponse.NetworkError -> {
            val message = UserMessage(AuthMessageKind.Backend.network())
            val errorMessages = uiState.userMessages + message
            _uiState = uiState.copy(userMessages = errorMessages)
        }
        is NetworkResponse.UnknownError -> {
            val message = UserMessage(AuthMessageKind.Backend.unknown())
            val errorMessages = uiState.userMessages + message
            _uiState = uiState.copy(userMessages = errorMessages)
        }
    }
}

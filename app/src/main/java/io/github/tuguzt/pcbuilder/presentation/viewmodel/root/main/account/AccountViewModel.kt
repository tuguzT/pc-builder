package io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.account

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.pcbuilder.data.datasource.remote.BackendResponse
import io.github.tuguzt.pcbuilder.data.repository.CurrentUserRepository
import io.github.tuguzt.pcbuilder.data.repository.UserTokenRepository
import io.github.tuguzt.pcbuilder.data.repository.UsersRepository
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.viewmodel.UserMessage
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import mu.KotlinLogging
import javax.inject.Inject

/**
 * Injectable view model which holds information about current user.
 */
@HiltViewModel
class AccountViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    private val tokenRepository: UserTokenRepository,
    private val googleSignInClient: GoogleSignInClient,
    private val currentUserRepository: CurrentUserRepository,
) : ViewModel() {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private var _uiState by mutableStateOf(AccountState())
    val uiState get() = _uiState

    private var updateJob: Job? = null

    fun updateUser(context: Context) {
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            _uiState = uiState.copy(isUpdating = true)
            tokenRepository.getToken() ?: kotlin.run {
                signOut()
                return@launch
            }

            usersRepository.current().handle(context) {
                currentUserRepository.updateCurrentUser(it)
                _uiState = uiState.copy(currentUser = it)
            }
            _uiState = uiState.copy(isUpdating = false)
        }
    }

    fun signOut() {
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            _uiState = uiState.copy(isUpdating = true)
            tokenRepository.setToken(null)
            googleSignInClient.signOut().await()
            currentUserRepository.updateCurrentUser(currentUser = null)
            _uiState = uiState.copy(currentUser = null, isUpdating = false)
        }
    }

    fun userMessageShown(messageId: NanoId) {
        val messages = uiState.userMessages.filterNot { it.id == messageId }
        _uiState = uiState.copy(userMessages = messages)
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

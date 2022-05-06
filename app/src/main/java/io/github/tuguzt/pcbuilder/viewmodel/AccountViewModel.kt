package io.github.tuguzt.pcbuilder.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.pcbuilder.domain.interactor.randomNanoId
import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import javax.inject.Inject

/**
 * Injectable view model which holds information about current user.
 */
@HiltViewModel
class AccountViewModel @Inject constructor() : ViewModel() {
    var currentUser: User? by mutableStateOf(mockUser())
}

private fun mockUser() = UserData(
    id = randomNanoId(),
    role = UserRole.Administrator,
    username = "tuguzT",
    email = "timurka.tugushev@gmail.com",
    imageUri = "https://avatars.githubusercontent.com/u/56771526",
)

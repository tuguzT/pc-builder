package io.github.tuguzt.pcbuilder.viewmodel.account

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData

// todo remove later
//  because we should move Google data retrieving to the server
fun GoogleSignInAccount.toUser(): User = UserData(
    role = UserRole.User,
    username = displayName.orEmpty(),
    email = email,
    imageUri = photoUrl?.toString(),
)

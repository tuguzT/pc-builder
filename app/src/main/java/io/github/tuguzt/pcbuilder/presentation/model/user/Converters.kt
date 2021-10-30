package io.github.tuguzt.pcbuilder.presentation.model.user

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

/**
 * Converts Google user to [User].
 */
fun GoogleSignInAccount.toUser(): User = when {
    email == Admin.email -> Admin.also { it.imageUri = photoUrl }
    email?.endsWith("@pc_builder.com") == true -> {
        Moderator(displayName!!, email!!, "no-password", photoUrl)
    }
    else -> UserOrdinal(displayName!!, email!!, "no-password", photoUrl)
}

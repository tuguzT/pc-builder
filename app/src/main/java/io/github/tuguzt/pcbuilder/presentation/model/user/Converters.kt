package io.github.tuguzt.pcbuilder.presentation.model.user

import android.net.Uri
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

/**
 * Creates [User] from the given [username], [email], [password] and [imageUri].
 *
 * @see User
 */
fun user(username: String, email: String, password: String, imageUri: Uri?): User = when {
    email == Admin.email -> Admin.also { it.imageUri = imageUri }
    email.endsWith("@pc_builder.com") -> {
        Moderator(username, email, password, imageUri)
    }
    else -> UserOrdinal(username, email, password, imageUri)
}

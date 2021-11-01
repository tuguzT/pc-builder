package io.github.tuguzt.pcbuilder.presentation.model.user

import android.net.Uri
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

/**
 * Converts Google user to [UserSealed].
 */
fun GoogleSignInAccount.toUser() = user(displayName!!, email!!, "no-password", photoUrl)

/**
 * Creates [UserSealed] from the given [username], [email], [password] and [imageUri].
 *
 * @see UserSealed
 */
fun user(username: String, email: String, password: String, imageUri: Uri?): UserSealed = when {
    email == Admin.email -> Admin.also { it.imageUri = imageUri?.toString() }
    email.endsWith("@pc_builder.com") -> Moderator(
        username = username,
        email = email,
        password = password,
        imageUri = imageUri?.toString(),
    )
    else -> UserOrdinal(
        username = username,
        email = email,
        password = password,
        imageUri = imageUri?.toString(),
    )
}

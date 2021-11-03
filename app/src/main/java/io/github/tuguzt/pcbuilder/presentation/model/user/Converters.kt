package io.github.tuguzt.pcbuilder.presentation.model.user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import io.github.tuguzt.pcbuilder.domain.randomNanoId

/**
 * Converts Google user to [UserSealed].
 */
fun GoogleSignInAccount.toUser() =
    user(username = displayName!!, email = email!!, password = "no-password", imageUri = photoUrl)

/**
 * Converts [UserSealed] to [Bundle].
 */
fun UserSealed.toBundle() = bundleOf(
    this::id.name to id,
    this::username.name to username,
    this::email.name to email,
    this::password.name to password,
    this::imageUri.name to imageUri,
)

/**
 * Converts [UserSealed] to [Intent].
 */
fun UserSealed.toIntent() = Intent().apply {
    putExtra(UserSealed::id.name, id)
    putExtra(UserSealed::username.name, username)
    putExtra(UserSealed::email.name, email)
    putExtra(UserSealed::password.name, password)
    putExtra(UserSealed::imageUri.name, imageUri)
}

/**
 * Converts [Bundle] to [UserSealed].
 */
fun Bundle.toUser() = user(
    getString(UserSealed::id.name)!!,
    getString(UserSealed::username.name)!!,
    getString(UserSealed::email.name)!!,
    getString(UserSealed::password.name)!!,
    getString(UserSealed::imageUri.name)?.let { Uri.parse(it) },
)

/**
 * Creates [UserSealed] from the given [username], [email], [password] and [imageUri].
 *
 * @see UserSealed
 */
fun user(
    id: String = randomNanoId(),
    username: String,
    email: String,
    password: String,
    imageUri: Uri?,
): UserSealed = when {
    email == Admin.email -> Admin.also { it.imageUri = imageUri?.toString() }
    email.endsWith("@pc_builder.com") ->
        Moderator(id, username, email, password, imageUri?.toString())
    else -> UserOrdinal(id, username, email, password, imageUri?.toString())
}

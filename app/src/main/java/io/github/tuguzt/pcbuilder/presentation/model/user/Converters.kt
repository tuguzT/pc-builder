package io.github.tuguzt.pcbuilder.presentation.model.user

import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole

fun GoogleSignInAccount.toUser() = UserData(
    email = email,
    imageUri = photoUrl?.toString(),
    role = UserRole.User,
)

/**
 * Converts [UserData] to [Bundle].
 */
fun UserData.toBundle() = bundleOf(
    this::id.name to id,
    this::email.name to email,
    this::imageUri.name to imageUri,
    this::role.name to role,
)

/**
 * Converts [UserData] to [Intent].
 */
fun UserData.toIntent() = Intent().apply {
    putExtra(UserData::id.name, id)
    putExtra(UserData::email.name, email)
    putExtra(UserData::imageUri.name, imageUri)
    putExtra(UserData::role.name, role)
}

/**
 * Converts [Bundle] to [UserData].
 */
fun Bundle.toUser() = UserData(
    requireNotNull(value = getString(UserData::id.name)),
    getString(UserData::email.name),
    getString(UserData::imageUri.name),
    getString(UserData::role.name)?.let { UserRole.valueOf(it) } ?: UserRole.User,
)

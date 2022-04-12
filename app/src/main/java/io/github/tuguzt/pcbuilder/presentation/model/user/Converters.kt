package io.github.tuguzt.pcbuilder.presentation.model.user

import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole

/**
 * Converts [GoogleSignInAccount] to [UserData].
 */
fun GoogleSignInAccount.toUser() = UserData(
    email = email,
    imageUri = photoUrl?.toString(),
    role = UserRole.User,
    username = requireNotNull(displayName),
)

/**
 * Converts [UserData] to [Bundle].
 */
fun UserData.toBundle() = bundleOf(
    this::id.name to id,
    this::username.name to username,
    this::email.name to email,
    this::imageUri.name to imageUri,
    this::role.name to role,
)

/**
 * Converts [UserData] to [Intent].
 */
fun UserData.toIntent() = Intent().apply { putExtras(toBundle()) }

/**
 * Converts [Bundle] to [UserData].
 */
fun Bundle.toUser() = UserData(
    id = requireNotNull(value = getString(UserData::id.name)),
    email = getString(UserData::email.name),
    imageUri = getString(UserData::imageUri.name),
    role = getString(UserData::role.name)?.let { UserRole.valueOf(it) } ?: UserRole.User,
    username = requireNotNull(value = getString(UserData::username.name))
)

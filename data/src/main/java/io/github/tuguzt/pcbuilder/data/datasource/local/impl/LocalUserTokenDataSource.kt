package io.github.tuguzt.pcbuilder.data.datasource.local.impl

import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import io.github.tuguzt.pcbuilder.data.datasource.UserTokenDataSource
import io.github.tuguzt.pcbuilder.domain.model.user.UserToken
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserTokenData

class LocalUserTokenDataSource(private val sharedPreferences: EncryptedSharedPreferences) :
    UserTokenDataSource {

    override suspend fun getToken(): UserToken? =
        sharedPreferences.getString("user_token", null)?.let { UserTokenData(it) }

    override suspend fun setToken(userToken: UserToken?) {
        sharedPreferences.edit {
            putString("user_token", userToken?.token)
        }
    }
}

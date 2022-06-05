package io.github.tuguzt.pcbuilder.data.datasource.local.impl

import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.data.datasource.UserTokenDataSource
import io.github.tuguzt.pcbuilder.data.toResult
import io.github.tuguzt.pcbuilder.domain.model.user.UserToken
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserTokenData

class LocalUserTokenDataSource(private val sharedPreferences: EncryptedSharedPreferences) :
    UserTokenDataSource {

    override suspend fun getToken(): Result<UserToken?, Error> =
        runCatching {
            sharedPreferences.getString("user_token", null)?.let { UserTokenData(it) }
        }.toResult()

    override suspend fun setToken(userToken: UserToken?): Result<Unit, Error> =
        runCatching {
            sharedPreferences.edit {
                putString("user_token", userToken?.token)
            }
        }.toResult()
}

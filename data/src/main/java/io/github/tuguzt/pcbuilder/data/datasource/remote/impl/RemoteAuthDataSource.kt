package io.github.tuguzt.pcbuilder.data.datasource.remote.impl

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.data.datasource.AuthDataSource
import io.github.tuguzt.pcbuilder.data.datasource.remote.api.BackendAuthAPI
import io.github.tuguzt.pcbuilder.data.datasource.remote.toResult
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserCredentialsData
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserTokenData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteAuthDataSource(private val backendAuthAPI: BackendAuthAPI) : AuthDataSource {
    override suspend fun auth(credentials: UserCredentialsData): Result<UserTokenData, Error> =
        withContext(Dispatchers.IO) { backendAuthAPI.auth(credentials) }.toResult()

    override suspend fun register(credentials: UserCredentialsData): Result<UserTokenData, Error> =
        withContext(Dispatchers.IO) { backendAuthAPI.register(credentials) }.toResult()

    override suspend fun googleOAuth2(authCode: UserTokenData): Result<UserTokenData, Error> =
        withContext(Dispatchers.IO) { backendAuthAPI.googleOAuth2(authCode) }.toResult()
}

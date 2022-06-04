package io.github.tuguzt.pcbuilder.data.datasource.remote.impl

import io.github.tuguzt.pcbuilder.data.datasource.AuthDataSource
import io.github.tuguzt.pcbuilder.data.datasource.remote.BackendAuthAPI
import io.github.tuguzt.pcbuilder.data.datasource.remote.BackendResponse
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserCredentialsData
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserTokenData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteAuthDataSource(private val backendAuthAPI: BackendAuthAPI) : AuthDataSource {
    override suspend fun auth(credentials: UserCredentialsData): BackendResponse<UserTokenData> =
        withContext(Dispatchers.IO) { backendAuthAPI.auth(credentials) }

    override suspend fun register(credentials: UserCredentialsData): BackendResponse<UserTokenData> =
        withContext(Dispatchers.IO) { backendAuthAPI.register(credentials) }

    override suspend fun googleOAuth2(authCode: UserTokenData): BackendResponse<UserTokenData> =
        withContext(Dispatchers.IO) { backendAuthAPI.googleOAuth2(authCode) }
}

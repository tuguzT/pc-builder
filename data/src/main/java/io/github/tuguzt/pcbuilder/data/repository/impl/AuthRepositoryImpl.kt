package io.github.tuguzt.pcbuilder.data.repository.impl

import io.github.tuguzt.pcbuilder.data.datasource.AuthDataSource
import io.github.tuguzt.pcbuilder.data.datasource.remote.BackendResponse
import io.github.tuguzt.pcbuilder.data.repository.AuthRepository
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserCredentialsData
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserTokenData

class AuthRepositoryImpl(private val dataSource: AuthDataSource) : AuthRepository {
    override suspend fun auth(credentials: UserCredentialsData): BackendResponse<UserTokenData> =
        dataSource.auth(credentials)

    override suspend fun register(credentials: UserCredentialsData): BackendResponse<UserTokenData> =
        dataSource.register(credentials)

    override suspend fun googleOAuth2(authCode: UserTokenData): BackendResponse<UserTokenData> =
        dataSource.googleOAuth2(authCode)
}

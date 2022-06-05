package io.github.tuguzt.pcbuilder.data.repository.impl

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.data.datasource.AuthDataSource
import io.github.tuguzt.pcbuilder.data.datasource.remote.BackendResponse
import io.github.tuguzt.pcbuilder.data.repository.AuthRepository
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserCredentialsData
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserTokenData

class AuthRepositoryImpl(private val dataSource: AuthDataSource) : AuthRepository {
    override suspend fun auth(credentials: UserCredentialsData): Result<UserTokenData, Error> =
        dataSource.auth(credentials)

    override suspend fun register(credentials: UserCredentialsData): Result<UserTokenData, Error> =
        dataSource.register(credentials)

    override suspend fun googleOAuth2(authCode: UserTokenData): Result<UserTokenData, Error> =
        dataSource.googleOAuth2(authCode)
}

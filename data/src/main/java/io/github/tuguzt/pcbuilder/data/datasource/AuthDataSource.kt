package io.github.tuguzt.pcbuilder.data.datasource

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserCredentialsData
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserTokenData

interface AuthDataSource {
    suspend fun auth(credentials: UserCredentialsData): Result<UserTokenData, Error>

    suspend fun register(credentials: UserCredentialsData): Result<UserTokenData, Error>

    suspend fun googleOAuth2(authCode: UserTokenData): Result<UserTokenData, Error>
}

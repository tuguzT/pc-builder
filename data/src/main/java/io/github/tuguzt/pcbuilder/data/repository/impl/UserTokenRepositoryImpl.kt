package io.github.tuguzt.pcbuilder.data.repository.impl

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.data.datasource.UserTokenDataSource
import io.github.tuguzt.pcbuilder.data.repository.UserTokenRepository
import io.github.tuguzt.pcbuilder.domain.model.user.UserToken

class UserTokenRepositoryImpl(private val dataSource: UserTokenDataSource) : UserTokenRepository {
    override suspend fun getToken(): Result<UserToken?, Error> = dataSource.getToken()

    override suspend fun setToken(userToken: UserToken?): Result<Unit, Error> =
        dataSource.setToken(userToken)
}

package io.github.tuguzt.pcbuilder.data.repository.impl

import io.github.tuguzt.pcbuilder.data.datasource.UserTokenDataSource
import io.github.tuguzt.pcbuilder.data.repository.UserTokenRepository
import io.github.tuguzt.pcbuilder.domain.model.user.UserToken

class UserTokenRepositoryImpl(private val dataSource: UserTokenDataSource) : UserTokenRepository {
    override suspend fun getToken(): UserToken? = dataSource.getToken()

    override suspend fun setToken(userToken: UserToken?): Unit = dataSource.setToken(userToken)
}

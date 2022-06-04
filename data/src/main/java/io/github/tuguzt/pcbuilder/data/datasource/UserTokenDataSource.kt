package io.github.tuguzt.pcbuilder.data.datasource

import io.github.tuguzt.pcbuilder.domain.model.user.UserToken

interface UserTokenDataSource {
    suspend fun getToken(): UserToken?

    suspend fun setToken(userToken: UserToken?)
}

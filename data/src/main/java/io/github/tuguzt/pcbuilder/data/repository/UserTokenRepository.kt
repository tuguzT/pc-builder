package io.github.tuguzt.pcbuilder.data.repository

import io.github.tuguzt.pcbuilder.domain.model.user.UserToken

interface UserTokenRepository {
    suspend fun getToken(): UserToken?

    suspend fun setToken(userToken: UserToken?)
}

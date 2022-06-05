package io.github.tuguzt.pcbuilder.data.datasource

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.domain.model.user.UserToken

interface UserTokenDataSource {
    suspend fun getToken(): Result<UserToken?, Error>

    suspend fun setToken(userToken: UserToken?): Result<Unit, Error>
}

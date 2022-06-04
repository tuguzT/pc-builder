package io.github.tuguzt.pcbuilder.data.datasource

import io.github.tuguzt.pcbuilder.data.datasource.remote.BackendResponse
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData

interface UsersDataSource {
    suspend fun getAll(): BackendResponse<List<UserData>>

    suspend fun current(): BackendResponse<UserData>

    suspend fun findById(id: NanoId): BackendResponse<UserData?>

    suspend fun findByUsername(username: String): BackendResponse<UserData?>
}

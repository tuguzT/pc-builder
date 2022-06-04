package io.github.tuguzt.pcbuilder.data.datasource.remote.impl

import io.github.tuguzt.pcbuilder.data.datasource.UsersDataSource
import io.github.tuguzt.pcbuilder.data.datasource.remote.BackendResponse
import io.github.tuguzt.pcbuilder.data.datasource.remote.BackendUsersAPI
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteUsersDataSource(private val backendUsersAPI: BackendUsersAPI) : UsersDataSource {
    override suspend fun getAll(): BackendResponse<List<UserData>> =
        withContext(Dispatchers.IO) { backendUsersAPI.getAll() }

    override suspend fun current(): BackendResponse<UserData> =
        withContext(Dispatchers.IO) { backendUsersAPI.current() }

    override suspend fun findById(id: NanoId): BackendResponse<UserData?> =
        withContext(Dispatchers.IO) { backendUsersAPI.findById(id = "$id") }

    override suspend fun findByUsername(username: String): BackendResponse<UserData?> =
        withContext(Dispatchers.IO) { backendUsersAPI.findByUsername(username) }
}

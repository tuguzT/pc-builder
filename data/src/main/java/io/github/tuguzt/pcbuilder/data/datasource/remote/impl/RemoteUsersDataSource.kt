package io.github.tuguzt.pcbuilder.data.datasource.remote.impl

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.data.datasource.UsersDataSource
import io.github.tuguzt.pcbuilder.data.datasource.remote.api.BackendUsersAPI
import io.github.tuguzt.pcbuilder.data.datasource.remote.toResult
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.PolymorphicComponent
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteUsersDataSource(private val backendUsersAPI: BackendUsersAPI) : UsersDataSource {
    override suspend fun getAll(): Result<List<UserData>, Error> =
        withContext(Dispatchers.IO) { backendUsersAPI.getAll() }.toResult()

    override suspend fun current(): Result<UserData, Error> =
        withContext(Dispatchers.IO) { backendUsersAPI.current() }.toResult()

    override suspend fun allFavoriteComponents(): Result<List<PolymorphicComponent>, Error> =
        withContext(Dispatchers.IO) { backendUsersAPI.allFavoriteComponents() }.toResult()

    override suspend fun addToFavorites(componentId: NanoId): Result<PolymorphicComponent, Error> =
        withContext(Dispatchers.IO) { backendUsersAPI.addToFavorites(componentId) }.toResult()

    override suspend fun removeFromFavorites(componentId: NanoId): Result<PolymorphicComponent, Error> =
        withContext(Dispatchers.IO) { backendUsersAPI.removeFromFavorites(componentId) }.toResult()

    override suspend fun findById(id: NanoId): Result<UserData?, Error> =
        withContext(Dispatchers.IO) { backendUsersAPI.findById(id) }.toResult()

    override suspend fun findByUsername(username: String): Result<UserData?, Error> =
        withContext(Dispatchers.IO) { backendUsersAPI.findByUsername(username) }.toResult()
}

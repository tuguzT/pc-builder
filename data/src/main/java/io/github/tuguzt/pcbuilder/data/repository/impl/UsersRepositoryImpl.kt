package io.github.tuguzt.pcbuilder.data.repository.impl

import io.github.tuguzt.pcbuilder.data.datasource.UsersDataSource
import io.github.tuguzt.pcbuilder.data.datasource.remote.BackendResponse
import io.github.tuguzt.pcbuilder.data.repository.UsersRepository
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData

class UsersRepositoryImpl(private val dataSource: UsersDataSource) : UsersRepository {
    override suspend fun getAll(): BackendResponse<List<UserData>> = dataSource.getAll()

    override suspend fun current(): BackendResponse<UserData> = dataSource.current()

    override suspend fun findById(id: NanoId): BackendResponse<UserData?> = dataSource.findById(id)

    override suspend fun findByUsername(username: String): BackendResponse<UserData?> =
        dataSource.findByUsername(username)
}

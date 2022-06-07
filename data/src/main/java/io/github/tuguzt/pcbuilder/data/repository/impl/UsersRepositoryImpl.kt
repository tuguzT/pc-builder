package io.github.tuguzt.pcbuilder.data.repository.impl

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.data.datasource.UsersDataSource
import io.github.tuguzt.pcbuilder.data.repository.UsersRepository
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.PolymorphicComponent
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData

class UsersRepositoryImpl(private val dataSource: UsersDataSource) : UsersRepository {
    override suspend fun getAll(): Result<List<UserData>, Error> = dataSource.getAll()

    override suspend fun current(): Result<UserData, Error> = dataSource.current()

    override suspend fun allFavoriteComponents(): Result<List<PolymorphicComponent>, Error> =
        dataSource.allFavoriteComponents()

    override suspend fun addToFavorites(componentId: NanoId): Result<PolymorphicComponent, Error> =
        dataSource.addToFavorites(componentId)

    override suspend fun removeFromFavorites(componentId: NanoId): Result<PolymorphicComponent, Error> =
        dataSource.removeFromFavorites(componentId)

    override suspend fun findById(id: NanoId): Result<UserData?, Error> = dataSource.findById(id)

    override suspend fun findByUsername(username: String): Result<UserData?, Error> =
        dataSource.findByUsername(username)
}

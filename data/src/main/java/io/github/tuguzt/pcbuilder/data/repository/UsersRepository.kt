package io.github.tuguzt.pcbuilder.data.repository

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.PolymorphicComponent
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData

interface UsersRepository {
    suspend fun getAll(): Result<List<UserData>, Error>

    suspend fun current(): Result<UserData, Error>

    suspend fun allFavoriteComponents(): Result<List<PolymorphicComponent>, Error>

    suspend fun addToFavorites(componentId: NanoId): Result<PolymorphicComponent, Error>

    suspend fun removeFromFavorites(componentId: NanoId): Result<PolymorphicComponent, Error>

    suspend fun findById(id: NanoId): Result<UserData?, Error>

    suspend fun findByUsername(username: String): Result<UserData?, Error>
}

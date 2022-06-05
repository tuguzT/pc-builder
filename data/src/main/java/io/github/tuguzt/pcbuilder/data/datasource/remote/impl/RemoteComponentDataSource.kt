package io.github.tuguzt.pcbuilder.data.datasource.remote.impl

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.data.datasource.ComponentDataSource
import io.github.tuguzt.pcbuilder.data.datasource.remote.api.BackendComponentsAPI
import io.github.tuguzt.pcbuilder.data.datasource.remote.toResult
import io.github.tuguzt.pcbuilder.data.mapSuccess
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData

class RemoteComponentDataSource(private val backendComponentsAPI: BackendComponentsAPI) :
    ComponentDataSource {

    override suspend fun findByName(name: String): Result<List<ComponentData>, Error> =
        backendComponentsAPI.findByName(name)
            .toResult()
            .mapSuccess { it?.let { listOf(it) } ?: emptyList() }

    override suspend fun getAll(): Result<List<ComponentData>, Error> =
        backendComponentsAPI.getAll().toResult()

    override suspend fun findById(id: NanoId): Result<ComponentData?, Error> =
        backendComponentsAPI.findById(id = "$id").toResult()

    override suspend fun save(item: ComponentData): Result<Unit, Error> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: ComponentData): Result<Unit, Error> {
        TODO("Not yet implemented")
    }

    override suspend fun clear(): Result<Unit, Error> {
        TODO("Not yet implemented")
    }
}
package io.github.tuguzt.pcbuilder.data.repository.impl

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.data.datasource.ComponentDataSource
import io.github.tuguzt.pcbuilder.data.repository.ComponentRepository
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData

class ComponentRepositoryImpl(private val dataSource: ComponentDataSource) : ComponentRepository {
    override suspend fun findByName(name: String): Result<List<ComponentData>, Error> =
        dataSource.findByName(name)

    override suspend fun getAll(): Result<List<ComponentData>, Error> = dataSource.getAll()

    override suspend fun findById(id: NanoId): Result<ComponentData?, Error> =
        dataSource.findById(id)

    override suspend fun save(item: ComponentData): Result<Unit, Error> = dataSource.save(item)

    override suspend fun delete(item: ComponentData): Result<Unit, Error> = dataSource.delete(item)

    override suspend fun clear(): Result<Unit, Error> = dataSource.clear()
}

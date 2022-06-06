package io.github.tuguzt.pcbuilder.data.repository.impl

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.data.datasource.ComponentDataSource
import io.github.tuguzt.pcbuilder.data.repository.ComponentRepository
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.PolymorphicComponent

class ComponentRepositoryImpl(private val dataSource: ComponentDataSource) : ComponentRepository {
    override suspend fun findByName(name: String): Result<List<PolymorphicComponent>, Error> =
        dataSource.findByName(name)

    override suspend fun getAll(): Result<List<PolymorphicComponent>, Error> = dataSource.getAll()

    override suspend fun findById(id: NanoId): Result<PolymorphicComponent?, Error> =
        dataSource.findById(id)

    override suspend fun save(item: PolymorphicComponent): Result<Unit, Error> =
        dataSource.save(item)

    override suspend fun delete(item: PolymorphicComponent): Result<Unit, Error> =
        dataSource.delete(item)

    override suspend fun clear(): Result<Unit, Error> = dataSource.clear()
}

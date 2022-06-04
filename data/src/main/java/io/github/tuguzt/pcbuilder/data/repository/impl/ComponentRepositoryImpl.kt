package io.github.tuguzt.pcbuilder.data.repository.impl

import io.github.tuguzt.pcbuilder.data.datasource.ComponentDataSource
import io.github.tuguzt.pcbuilder.data.repository.ComponentRepository
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData

class ComponentRepositoryImpl(private val dataSource: ComponentDataSource) : ComponentRepository {
    override suspend fun findByName(name: String): List<ComponentData> =
        dataSource.findByName(name)

    override suspend fun getAll(): List<ComponentData> = dataSource.getAll()

    override suspend fun findById(id: NanoId): ComponentData? = dataSource.findById(id)

    override suspend fun save(item: ComponentData): Unit = dataSource.save(item)

    override suspend fun delete(item: ComponentData): Unit = dataSource.delete(item)

    override suspend fun clear(): Unit = dataSource.clear()
}

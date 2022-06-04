package io.github.tuguzt.pcbuilder.data.repository.impl

import io.github.tuguzt.pcbuilder.data.datasource.ManufacturerDataSource
import io.github.tuguzt.pcbuilder.data.repository.ManufacturerRepository
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData

class ManufacturerRepositoryImpl(private val dataSource: ManufacturerDataSource) :
    ManufacturerRepository {

    override suspend fun getAll(): List<ManufacturerData> = dataSource.getAll()

    override suspend fun findById(id: NanoId): ManufacturerData? = dataSource.findById(id)

    override suspend fun save(item: ManufacturerData): Unit = dataSource.save(item)

    override suspend fun delete(item: ManufacturerData): Unit = dataSource.delete(item)

    override suspend fun clear(): Unit = dataSource.clear()
}

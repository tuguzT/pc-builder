package io.github.tuguzt.pcbuilder.data.repository.impl

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.data.datasource.ManufacturerDataSource
import io.github.tuguzt.pcbuilder.data.repository.ManufacturerRepository
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData

class ManufacturerRepositoryImpl(private val dataSource: ManufacturerDataSource) :
    ManufacturerRepository {

    override suspend fun getAll(): Result<List<ManufacturerData>, Error> = dataSource.getAll()

    override suspend fun findById(id: NanoId): Result<ManufacturerData?, Error> = dataSource.findById(id)

    override suspend fun save(item: ManufacturerData): Result<Unit, Error> = dataSource.save(item)

    override suspend fun delete(item: ManufacturerData): Result<Unit, Error> = dataSource.delete(item)

    override suspend fun clear(): Result<Unit, Error> = dataSource.clear()
}

package io.github.tuguzt.pcbuilder.data.repository.impl

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.data.datasource.BuildDataSource
import io.github.tuguzt.pcbuilder.data.repository.BuildRepository
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.build.BuildData

class BuildRepositoryImpl(private val dataSource: BuildDataSource) : BuildRepository {
    override suspend fun getAll(): Result<List<BuildData>, Error> = dataSource.getAll()

    override suspend fun findById(id: NanoId): Result<BuildData?, Error> = dataSource.findById(id)

    override suspend fun save(item: BuildData): Result<Unit, Error> = dataSource.save(item)

    override suspend fun delete(item: BuildData): Result<Unit, Error> = dataSource.delete(item)

    override suspend fun clear(): Result<Unit, Error> = dataSource.clear()
}

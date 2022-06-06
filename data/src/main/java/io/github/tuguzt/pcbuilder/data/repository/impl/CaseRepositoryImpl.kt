package io.github.tuguzt.pcbuilder.data.repository.impl

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.data.datasource.CaseDataSource
import io.github.tuguzt.pcbuilder.data.repository.CaseRepository
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.CaseData

class CaseRepositoryImpl(private val dataSource: CaseDataSource) : CaseRepository {
    override suspend fun getAll(): Result<List<CaseData>, Error> = dataSource.getAll()

    override suspend fun findById(id: NanoId): Result<CaseData?, Error> = dataSource.findById(id)

    override suspend fun save(item: CaseData): Result<Unit, Error> = dataSource.save(item)

    override suspend fun delete(item: CaseData): Result<Unit, Error> = dataSource.delete(item)

    override suspend fun clear(): Result<Unit, Error> = dataSource.clear()
}

package io.github.tuguzt.pcbuilder.data.repository.impl

import io.github.tuguzt.pcbuilder.data.datasource.CaseDataSource
import io.github.tuguzt.pcbuilder.data.repository.CaseRepository
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.cases.data.CaseData

class CaseRepositoryImpl(private val dataSource: CaseDataSource) : CaseRepository {
    override suspend fun getAll(): List<CaseData> = dataSource.getAll()

    override suspend fun findById(id: NanoId): CaseData? = dataSource.findById(id)

    override suspend fun save(item: CaseData): Unit = dataSource.save(item)

    override suspend fun delete(item: CaseData): Unit = dataSource.delete(item)

    override suspend fun clear(): Unit = dataSource.clear()
}

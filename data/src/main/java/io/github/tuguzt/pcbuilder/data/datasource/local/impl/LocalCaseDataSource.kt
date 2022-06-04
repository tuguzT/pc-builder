package io.github.tuguzt.pcbuilder.data.datasource.local.impl

import io.github.tuguzt.pcbuilder.data.datasource.CaseDataSource
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dao.CaseDao
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.cases.CaseDto
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.cases.CaseMotherboardFormFactorCrossRef
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.cases.toDomain
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.motherboard.MotherboardFormFactorDto
import io.github.tuguzt.pcbuilder.data.datasource.local.room.toEntity
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.cases.CasePowerSupply
import io.github.tuguzt.pcbuilder.domain.model.component.cases.data.CaseData
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardFormFactor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalCaseDataSource(
    private val caseDao: CaseDao,
    private val componentDataSource: LocalComponentDataSource,
    private val motherboardFormFactorDataSource: LocalMotherboardFormFactorDataSource,
    private val caseMotherboardFormFactorCrossRefDataSource: LocalCaseMotherboardFormFactorCrossRefDataSource,
) : CaseDataSource {

    companion object {
        private fun makeCaseData(
            caseEntity: CaseDto,
            component: ComponentData,
            motherboardFormFactors: List<MotherboardFormFactor>,
        ) = CaseData(
            id = component.id,
            name = component.name,
            description = component.description,
            weight = component.weight,
            size = component.size,
            manufacturer = component.manufacturer,
            driveBays = caseEntity.driveBays.toDomain(),
            expansionSlots = caseEntity.expansionSlots.toDomain(),
            motherboardFormFactors = motherboardFormFactors,
            powerSupply = caseEntity.powerSupply?.let(::CasePowerSupply),
            powerSupplyShroud = caseEntity.powerSupplyShroud,
            sidePanelWindow = caseEntity.sidePanelWindow,
            type = caseEntity.type,
        )
    }

    override suspend fun getAll(): List<CaseData> = caseDao.getAll().map { caseEntity ->
        val component = componentDataSource.findById(NanoId(caseEntity.caseId))!!
        val motherboardFormFactors = caseMotherboardFormFactorCrossRefDataSource
            .findByCaseId(caseEntity.id)
            .map { motherboardFormFactorDataSource.findById(it.motherboardFormFactorId)!!.id }
        makeCaseData(caseEntity, component, motherboardFormFactors)
    }

    override suspend fun findById(id: NanoId): CaseData? = caseDao.findById(id.toString())
        ?.let { entity ->
            val component = componentDataSource.findById(NanoId(entity.caseId))!!
            val motherboardFormFactors = caseMotherboardFormFactorCrossRefDataSource
                .findByCaseId(entity.id)
                .map { motherboardFormFactorDataSource.findById(it.motherboardFormFactorId)!!.id }
            makeCaseData(entity, component, motherboardFormFactors)
        }

    override suspend fun save(item: CaseData): Unit = withContext(Dispatchers.IO) {
        val componentData = ComponentData(
            id = item.id,
            name = item.name,
            description = item.description,
            weight = item.weight,
            size = item.size,
            manufacturer = item.manufacturer,
        )
        componentDataSource.save(componentData)
        item.motherboardFormFactors.forEach {
            motherboardFormFactorDataSource.save(MotherboardFormFactorDto(it))
            val crossRef = CaseMotherboardFormFactorCrossRef(
                caseId = item.id.toString(),
                motherboardFormFactorId = it,
            )
            caseMotherboardFormFactorCrossRefDataSource.save(crossRef)
        }
        if (caseDao.findById(item.id.toString()) == null) {
            caseDao.insert(item.toEntity())
        }
        caseDao.update(item.toEntity())
    }

    override suspend fun delete(item: CaseData): Unit =
        withContext(Dispatchers.IO) { caseDao.delete(item.toEntity()) }

    override suspend fun clear() = withContext(Dispatchers.IO) { caseDao.clear() }
}

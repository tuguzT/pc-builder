package io.github.tuguzt.pcbuilder.repository.room.impl

import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.cases.CasePowerSupply
import io.github.tuguzt.pcbuilder.domain.model.component.cases.data.CaseData
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardFormFactor
import io.github.tuguzt.pcbuilder.repository.Repository
import io.github.tuguzt.pcbuilder.repository.room.dao.CaseDao
import io.github.tuguzt.pcbuilder.repository.room.dto.component.cases.CaseEntity
import io.github.tuguzt.pcbuilder.repository.room.dto.component.cases.CaseMotherboardFormFactorCrossRef
import io.github.tuguzt.pcbuilder.repository.room.dto.component.cases.toDomain
import io.github.tuguzt.pcbuilder.repository.room.dto.component.motherboard.MotherboardFormFactorEntity
import io.github.tuguzt.pcbuilder.repository.room.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RoomCaseRepository(
    private val caseDao: CaseDao,
    private val componentRepository: RoomComponentRepository,
    private val motherboardFormFactorRepository: RoomMotherboardFormFactorRepository,
    private val caseMotherboardFormFactorCrossRefRepository: RoomCaseMotherboardFormFactorCrossRefRepository,
) : Repository<NanoId, CaseData> {

    companion object {
        private fun makeCaseData(
            caseEntity: CaseEntity,
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

    override fun getAll(): Flow<List<CaseData>> = caseDao.getAll()
        .map { caseEntities ->
            caseEntities.map { caseEntity ->
                val component = componentRepository.findByIdNow(NanoId(caseEntity.caseId))!!
                val motherboardFormFactors = caseMotherboardFormFactorCrossRefRepository
                    .findByCaseIdNow(caseEntity.id)
                    .map { motherboardFormFactorRepository.findByIdNow(it.motherboardFormFactorId)!!.id }
                makeCaseData(caseEntity, component, motherboardFormFactors)
            }
        }

    override fun findById(id: NanoId): Flow<CaseData> = caseDao.findById(id.toString())
        .map { caseEntity ->
            val component = componentRepository.findByIdNow(NanoId(caseEntity.caseId))!!
            val motherboardFormFactors = caseMotherboardFormFactorCrossRefRepository
                .findByCaseIdNow(caseEntity.id)
                .map { motherboardFormFactorRepository.findByIdNow(it.motherboardFormFactorId)!!.id }
            makeCaseData(caseEntity, component, motherboardFormFactors)
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
        componentRepository.save(componentData)
        item.motherboardFormFactors.forEach {
            motherboardFormFactorRepository.save(MotherboardFormFactorEntity(it))
            val crossRef = CaseMotherboardFormFactorCrossRef(
                caseId = item.id.toString(),
                motherboardFormFactorId = it,
            )
            caseMotherboardFormFactorCrossRefRepository.save(crossRef)
        }
        if (caseDao.findByIdNow(item.id.toString()) == null) {
            caseDao.insert(item.toEntity())
        }
        caseDao.update(item.toEntity())
    }

    override suspend fun delete(item: CaseData): Unit =
        withContext(Dispatchers.IO) { caseDao.delete(item.toEntity()) }

    override suspend fun clear() = withContext(Dispatchers.IO) { caseDao.clear() }
}

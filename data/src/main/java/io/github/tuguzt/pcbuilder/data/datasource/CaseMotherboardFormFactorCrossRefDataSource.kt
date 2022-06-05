package io.github.tuguzt.pcbuilder.data.datasource

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.cases.CaseMotherboardFormFactorCrossRef

interface CaseMotherboardFormFactorCrossRefDataSource :
    CrudDataSource<CaseMotherboardFormFactorCrossRef, CaseMotherboardFormFactorCrossRef> {

    suspend fun findByCaseId(caseId: String): Result<List<CaseMotherboardFormFactorCrossRef>, Error>
}

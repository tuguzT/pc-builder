package io.github.tuguzt.pcbuilder.data.datasource

import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.cases.data.CaseData

interface CaseDataSource : CrudDataSource<NanoId, CaseData>

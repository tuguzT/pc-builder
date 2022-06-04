package io.github.tuguzt.pcbuilder.data.datasource

import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData

interface ManufacturerDataSource : CrudDataSource<NanoId, ManufacturerData>

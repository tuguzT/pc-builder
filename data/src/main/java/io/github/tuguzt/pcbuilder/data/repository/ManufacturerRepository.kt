package io.github.tuguzt.pcbuilder.data.repository

import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData

interface ManufacturerRepository : CrudRepository<NanoId, ManufacturerData>

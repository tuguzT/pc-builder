package io.github.tuguzt.pcbuilder.data.repository

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData

interface ComponentRepository : CrudRepository<NanoId, ComponentData> {
    suspend fun findByName(name: String): Result<List<ComponentData>, Error>
}

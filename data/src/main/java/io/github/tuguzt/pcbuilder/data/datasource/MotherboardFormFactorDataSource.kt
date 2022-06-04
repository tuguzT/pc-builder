package io.github.tuguzt.pcbuilder.data.datasource

import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.motherboard.MotherboardFormFactorDto
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardFormFactor

interface MotherboardFormFactorDataSource :
    CrudDataSource<MotherboardFormFactor, MotherboardFormFactorDto>

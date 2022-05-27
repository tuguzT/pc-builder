package io.github.tuguzt.pcbuilder.repository.room.dto.component

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.repository.room.converters.LengthConverter
import io.github.tuguzt.pcbuilder.repository.room.converters.MassConverter
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Measure

/**
 * Data Transfer Object for [Component].
 *
 * @see Component
 */
@Entity(tableName = "component")
@TypeConverters(MassConverter::class, LengthConverter::class)
data class ComponentDto(
    @PrimaryKey override val id: String,
    override val name: String,
    override val description: String,
    override val weight: Measure<Mass>,
    @Embedded override val size: Size,
    val imageUri: String?,
) : Component

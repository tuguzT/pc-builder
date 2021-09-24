package io.github.tuguzt.pcbuilder.presentation.repository.room.dto

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.github.tuguzt.pcbuilder.domain.model.Component
import io.github.tuguzt.pcbuilder.domain.model.Size
import io.github.tuguzt.pcbuilder.presentation.repository.room.converters.DistanceConverter
import io.github.tuguzt.pcbuilder.presentation.repository.room.converters.MassConverter
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Measure

/**
 * Data Transfer Object for [Component].
 *
 * @see Component
 */
@Entity(tableName = "component")
@TypeConverters(MassConverter::class, DistanceConverter::class)
data class ComponentDto(
    override val name: String,
    override val description: String,
    override val weight: Measure<Mass>,
    @Embedded override val size: Size,
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
) : Component

package com.mirea.tuguzt.pcbuilder.presentation.repository.dto

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mirea.tuguzt.pcbuilder.domain.model.Component
import com.mirea.tuguzt.pcbuilder.domain.model.Size
import com.mirea.tuguzt.pcbuilder.presentation.repository.converters.DistanceConverter
import com.mirea.tuguzt.pcbuilder.presentation.repository.converters.MassConverter
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Measure

/**
 * Data Transfer Object for [Component].
 *
 * @see Component
 */
@Entity(tableName = "component")
@TypeConverters(MassConverter::class, DistanceConverter::class)
data class ComponentDTO(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    override val name: String,
    override val description: String,
    override val weight: Measure<Mass>,
    @Embedded override val size: Size,
) : Component

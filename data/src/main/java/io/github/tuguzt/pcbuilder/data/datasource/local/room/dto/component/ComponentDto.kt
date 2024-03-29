package io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.github.tuguzt.pcbuilder.data.datasource.local.room.converters.LengthConverter
import io.github.tuguzt.pcbuilder.data.datasource.local.room.converters.MassConverter
import io.github.tuguzt.pcbuilder.domain.model.Identifiable
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Measure

@Entity(tableName = "component")
@TypeConverters(MassConverter::class, LengthConverter::class)
data class ComponentDto(
    @PrimaryKey val componentId: String,
    val name: String,
    val description: String,
    val weight: Measure<Mass>,
    @Embedded val size: Size,
    val manufacturerId: String,
) : Identifiable<String> {
    override val id: String get() = componentId
}

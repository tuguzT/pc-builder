package io.github.tuguzt.pcbuilder.repository.room.dto.component

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.tuguzt.pcbuilder.domain.model.Identifiable

@Entity(tableName = "manufacturer")
data class ManufacturerEntity(
    @PrimaryKey val manufacturerId: String,
    val name: String,
    val description: String,
) : Identifiable<String> {
    override val id: String get() = manufacturerId
}

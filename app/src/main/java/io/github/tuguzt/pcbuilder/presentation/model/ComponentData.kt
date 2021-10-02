package io.github.tuguzt.pcbuilder.presentation.model

import android.os.Parcelable
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.presentation.model.parceler.MassParceler
import io.github.tuguzt.pcbuilder.presentation.model.parceler.SizeParceler
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.ComponentDto
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Measure
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.WriteWith

@Parcelize
data class ComponentData(
    override val id: String,
    override val name: String,
    override val description: String,
    override val weight: @WriteWith<MassParceler> Measure<Mass>,
    override val size: @WriteWith<SizeParceler> Size,
    val imageUri: String?,
) : Component, Parcelable {
    constructor(c: Component) : this(c.id, c.name, c.description, c.weight, c.size, null)
    constructor(c: ComponentDto) : this(c.id, c.name, c.description, c.weight, c.size, c.imageUri)
}

package io.github.tuguzt.pcbuilder.presentation.model

import android.os.Parcelable
import io.github.tuguzt.pcbuilder.domain.model.Component
import io.github.tuguzt.pcbuilder.domain.model.Size
import io.github.tuguzt.pcbuilder.presentation.model.parceler.MassParceler
import io.github.tuguzt.pcbuilder.presentation.model.parceler.SizeParceler
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Measure
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.WriteWith

@Parcelize
data class ComponentData(
    override val name: String,
    override val description: String,
    override val weight: @WriteWith<MassParceler>() Measure<Mass>,
    override val size: @WriteWith<SizeParceler>() Size,
) : Component, Parcelable {
    constructor(component: Component) : this(
        component.name,
        component.description,
        component.weight,
        component.size,
    )
}

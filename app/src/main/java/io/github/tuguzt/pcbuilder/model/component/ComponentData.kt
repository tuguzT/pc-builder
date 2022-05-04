@file:UseSerializers(MeasureSerializer::class, MassSerializer::class)

package io.github.tuguzt.pcbuilder.model.component

import io.github.tuguzt.pcbuilder.domain.interactor.serialization.measured.MassSerializer
import io.github.tuguzt.pcbuilder.domain.interactor.serialization.measured.MeasureSerializer
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Measure
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

/**
 * Represents data of the [component][Component].
 */
@Serializable
data class ComponentData(
    override val id: String,
    override val name: String,
    override val description: String,
    override val weight: Measure<Mass>,
    override val size: Size,
) : Component

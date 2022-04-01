package io.github.tuguzt.pcbuilder.presentation.model.component

import android.os.Parcelable
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.presentation.model.component.parceler.MassParceler
import io.github.tuguzt.pcbuilder.presentation.model.component.parceler.SizeParceler
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Measure
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.WriteWith

/**
 * Data class for component data used in application.
 *
 * @see Component
 */
@Parcelize
data class ComponentData(
    override val id: String,
    override val name: String,
    override val description: String,
    override val weight: @WriteWith<MassParceler> Measure<Mass>,
    override val size: @WriteWith<SizeParceler> Size,
    val imageUri: String?,
) : Component, Parcelable

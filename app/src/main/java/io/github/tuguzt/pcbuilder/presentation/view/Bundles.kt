package io.github.tuguzt.pcbuilder.presentation.view

import android.os.Bundle
import androidx.core.os.bundleOf
import io.github.tuguzt.pcbuilder.domain.model.Component
import io.github.tuguzt.pcbuilder.domain.model.Size
import io.nacular.measured.units.Length.Companion.meters
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.times

/**
 * Converts [Component] to [Bundle].
 *
 * @return bundle object with information about component.
 */
fun Component.toBundle() = bundleOf(
    "name" to name,
    "description" to description,
    "weight" to (weight `in` grams),
    "length" to (size.length `in` meters),
    "width" to (size.width `in` meters),
    "height" to (size.height `in` meters),
)

/**
 * Converts [Bundle] to [Component].
 *
 * @return component object with information from bundle.
 */
fun Bundle.toComponent(): Component {
    val name = getString("name")!!
    val description = getString("description")!!
    val weight = getDouble("weight") * grams
    val size = Size(
        length = getDouble("length") * meters,
        width = getDouble("width") * meters,
        height = getDouble("height") * meters,
    )
    return object : Component {
        override val name = name
        override val description = description
        override val weight = weight
        override val size = size
    }
}

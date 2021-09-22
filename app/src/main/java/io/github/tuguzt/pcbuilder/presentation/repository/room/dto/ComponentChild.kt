package io.github.tuguzt.pcbuilder.presentation.repository.room.dto

import io.github.tuguzt.pcbuilder.domain.model.Component
import io.github.tuguzt.pcbuilder.domain.model.Size
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Measure
import kotlin.reflect.KProperty

/**
 * Interface for entities which have foreign key [`id`][ComponentDTO.id]
 * from [`component`][ComponentDTO] table.
 *
 * On attempt to get any field of [Component] will throw an exception
 * (because child table mustn't duplicate data from the [parent table][ComponentDTO]).
 *
 * @see ComponentDTO
 * @see Component
 */
internal interface ComponentChild : Component {
    override val name: String get() = noField(this::name)
    override val description: String get() = noField(this::description)
    override val weight: Measure<Mass> get() = noField(this::weight)
    override val size: Size get() = noField(this::size)
}

/**
 * Always throws an exception.
 *
 * @throws [NoSuchFieldException] with detailed message.
 */
private inline fun <reified F> noField(property: KProperty<F>): Nothing =
    throw NoSuchFieldException("DTO have no field with name ${property.name} and type ${F::class.simpleName}")

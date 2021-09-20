package com.mirea.tuguzt.pcbuilder.presentation.repository.mock

import com.mirea.tuguzt.pcbuilder.domain.model.Component
import com.mirea.tuguzt.pcbuilder.domain.model.Size
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Measure

/**
 * [Component] subclass used for mocking.
 */
data class MockComponent(
    override val name: String,
    override val description: String,
    override val weight: Measure<Mass>,
    override val size: Size,
) : Component

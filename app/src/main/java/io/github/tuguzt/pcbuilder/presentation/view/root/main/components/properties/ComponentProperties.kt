package io.github.tuguzt.pcbuilder.presentation.view.root.main.components.properties

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.asMeasure
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.utils.SimpleProperty
import io.github.tuguzt.pcbuilder.presentation.view.utils.round
import io.nacular.measured.units.Length
import io.nacular.measured.units.Mass

@Composable
fun ComponentProperties(component: Component, modifier: Modifier = Modifier) = Column(modifier) {
    SimpleProperty(
        name = stringResource(R.string.name),
        value = component.name,
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.description),
        value = component.description,
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.weight),
        value = run {
            val value = component.weight.asMeasure() `in` Mass.grams
            "${value.round(3)} ${stringResource(R.string.unit_gram)}"
        },
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.length),
        value = run {
            val value = component.size.length `in` Length.millimeters
            "${value.round(3)} ${stringResource(R.string.unit_millimeter)}"
        },
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.width),
        value = run {
            val value = component.size.width `in` Length.millimeters
            "${value.round(3)} ${stringResource(R.string.unit_millimeter)}"
        },
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.height),
        value = run {
            val value = component.size.height `in` Length.millimeters
            "${value.round(3)} ${stringResource(R.string.unit_millimeter)}"
        },
    )
}

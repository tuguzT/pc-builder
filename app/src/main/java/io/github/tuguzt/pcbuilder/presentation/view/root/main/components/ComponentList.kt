package io.github.tuguzt.pcbuilder.presentation.view.root.main.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.domain.model.component.Weight
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
import io.github.tuguzt.pcbuilder.presentation.view.theme.PCBuilderTheme
import io.nacular.measured.units.Length
import io.nacular.measured.units.Mass.Companion.kilograms
import io.nacular.measured.units.times

/**
 * Lazy list of provided [components].
 */
@Composable
fun ComponentList(
    components: List<Component>,
    onComponentClick: (Component) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(components, key = { "${it.id}" }) { component ->
            ComponentItem(
                component = component,
                onClick = { onComponentClick(component) }
            )
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun ComponentListPreview() {
    PCBuilderTheme {
        val components = List(5) {
            ComponentData(
                name = "NVIDIA GeForce RTX 3050",
                description = "The RTX 3050 is built on Ampere architecture and uses 8GB" +
                        " of GDDR6 VRAM. This is the same memory found in the RTX 3060 Ti." +
                        " The card has 2,560 CUDA cores with a base clock of 1.55 GHz and" +
                        " a boost clock of 1.78 GHz. It also has a 128-bit memory interface width" +
                        " seen in GPUs targeting 1080p.",
                weight = Weight(1 * kilograms),
                size = Size(
                    length = 242 * Length.millimeters,
                    width = 112 * Length.millimeters,
                    height = 40 * Length.millimeters,
                ),
                manufacturer = ManufacturerData(
                    name = "Example",
                    description = "Hello World",
                ),
            )
        }
        Surface {
            ComponentList(
                components = components,
                onComponentClick = {}
            )
        }
    }
}
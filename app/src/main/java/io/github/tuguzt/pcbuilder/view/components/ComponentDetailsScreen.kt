package io.github.tuguzt.pcbuilder.view.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.domain.model.component.Weight
import io.github.tuguzt.pcbuilder.domain.model.component.asMeasure
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
import io.github.tuguzt.pcbuilder.view.theme.PCBuilderTheme
import io.nacular.measured.units.Length.Companion.millimeters
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.Mass.Companion.kilograms
import io.nacular.measured.units.times

/**
 * Application screen with information about provided [component].
 */
@Composable
fun ComponentDetailsScreen(component: Component, painter: Painter? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        val imageShape = MaterialTheme.shapes.medium.copy(
            topStart = ZeroCornerSize,
            topEnd = ZeroCornerSize,
        )
        Surface(tonalElevation = 5.dp, shape = imageShape) {
            Image(
                modifier = Modifier
                    .heightIn(min = 240.dp)
                    .fillMaxWidth(),
                painter = painter ?: ColorPainter(Color.Transparent),
                contentDescription = stringResource(R.string.component_picture),
            )
        }

        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
        ) {
            ComponentProperty(
                name = stringResource(R.string.description),
                value = component.description,
            )

            Divider()
            ComponentProperty(
                name = stringResource(R.string.weight),
                value = "${component.weight.asMeasure() `in` grams} ${stringResource(R.string.unit_gram)}",
            )

            Divider()
            ComponentProperty(
                name = stringResource(R.string.length),
                value = "${component.size.length `in` millimeters} " +
                        stringResource(R.string.unit_millimeter),
            )

            Divider()
            ComponentProperty(
                name = stringResource(R.string.width),
                value = "${component.size.width `in` millimeters} " +
                        stringResource(R.string.unit_millimeter),
            )

            Divider()
            ComponentProperty(
                name = stringResource(R.string.height),
                value = "${component.size.height `in` millimeters} " +
                        stringResource(R.string.unit_millimeter)
            )
        }
    }
}

/**
 * Property of the component with provided [name] and [value].
 */
@Composable
fun ComponentProperty(name: String, value: String) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = name,
        style = MaterialTheme.typography.labelMedium,
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = value,
        style = MaterialTheme.typography.bodyLarge,
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun ComponentDetailsScreenPreview() {
    PCBuilderTheme {
        val component = ComponentData(
            name = "NVIDIA GeForce RTX 3050",
            description = "The RTX 3050 is built on Ampere architecture and uses 8GB" +
                    " of GDDR6 VRAM. This is the same memory found in the RTX 3060 Ti." +
                    " The card has 2,560 CUDA cores with a base clock of 1.55 GHz and" +
                    " a boost clock of 1.78 GHz. It also has a 128-bit memory interface width" +
                    " seen in GPUs targeting 1080p.",
            weight = Weight(1 * kilograms),
            size = Size(
                length = 242 * millimeters,
                width = 112 * millimeters,
                height = 40 * millimeters,
            ),
            manufacturer = ManufacturerData(
                name = "Example",
                description = "Hello World",
            ),
        )
        Surface {
            ComponentDetailsScreen(component)
        }
    }
}

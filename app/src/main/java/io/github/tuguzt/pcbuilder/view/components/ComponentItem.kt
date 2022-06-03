package io.github.tuguzt.pcbuilder.view.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.domain.model.component.Weight
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
import io.github.tuguzt.pcbuilder.view.theme.PCBuilderTheme
import io.nacular.measured.units.Length.Companion.millimeters
import io.nacular.measured.units.Mass.Companion.kilograms
import io.nacular.measured.units.times

/**
 * [Card] composable with data of the provided [component]
 * and optional image provided by [painter].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponentItem(
    component: Component,
    painter: Painter? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    onClick: () -> Unit,
) {
    Card(shape = shape, onClick = onClick) {
        Column {
            Surface(tonalElevation = 5.dp, shape = shape) {
                Image(
                    painter = painter ?: ColorPainter(Color.Transparent),
                    contentDescription = painter?.let { stringResource(R.string.component_picture) },
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(194.dp)
                        .fillMaxWidth(),
                )
            }
            Column(modifier = Modifier.padding(8.dp)) {
                DisableSelection {
                    Text(
                        text = component.name,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = component.description,
                        maxLines = 2,
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun ComponentItemPreview() {
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
            ComponentItem(
                component = component,
                painter = painterResource(R.drawable.ic_launcher_background),
                onClick = {},
            )
        }
    }
}

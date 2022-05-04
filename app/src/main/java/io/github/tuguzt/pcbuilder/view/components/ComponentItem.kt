package io.github.tuguzt.pcbuilder.view.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.model.component.ComponentData
import io.github.tuguzt.pcbuilder.view.theme.PCBuilderTheme
import io.nacular.measured.units.Length.Companion.millimeters
import io.nacular.measured.units.Mass.Companion.kilograms
import io.nacular.measured.units.times

/**
 * [Card] composable with data of the provided [component]
 * and optional image provided by [painter].
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ComponentItem(component: ComponentData, painter: Painter? = null, onClick: () -> Unit) {
    Card(shape = RoundedCornerShape(8.dp), onClick = onClick) {
        Column {
            when (painter) {
                null -> Spacer(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f)),
                )
                else -> Image(
                    painter = painter,
                    contentDescription = stringResource(R.string.component_picture),
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f)),
                )
            }
            Column(modifier = Modifier.padding(8.dp)) {
                DisableSelection {
                    Text(
                        text = component.name,
                        style = MaterialTheme.typography.h6,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = component.description,
                        maxLines = 2,
                        style = MaterialTheme.typography.body2,
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
            id = "id",
            name = "NVIDIA GeForce RTX 3050",
            description = "The RTX 3050 is built on Ampere architecture and uses 8GB" +
                    " of GDDR6 VRAM. This is the same memory found in the RTX 3060 Ti." +
                    " The card has 2,560 CUDA cores with a base clock of 1.55 GHz and" +
                    " a boost clock of 1.78 GHz. It also has a 128-bit memory interface width" +
                    " seen in GPUs targeting 1080p.",
            weight = 1 * kilograms,
            size = Size(
                length = 242 * millimeters,
                width = 112 * millimeters,
                height = 40 * millimeters,
            ),
        )
        ComponentItem(
            component = component,
            painter = painterResource(R.drawable.ic_launcher_background),
            onClick = {}
        )
    }
}

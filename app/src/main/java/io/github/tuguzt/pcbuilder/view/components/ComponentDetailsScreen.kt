package io.github.tuguzt.pcbuilder.view.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.ComponentData
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.view.theme.PCBuilderTheme
import io.nacular.measured.units.times
import io.github.tuguzt.pcbuilder.R
import io.nacular.measured.units.Length.Companion.millimeters
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.Mass.Companion.kilograms

/**
 * Application screen with information about provided [component].
 */
@Composable
fun ComponentDetailsScreen(component: Component) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Image(
            modifier = Modifier.height(240.dp),
            painter = ColorPainter(MaterialTheme.colors.onSurface.copy(alpha = 0.2f)),
            contentDescription = stringResource(R.string.component_picture),
        )

        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
        ) {
            Text(
                text = component.name,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = component.description,
                style = MaterialTheme.typography.body2,
            )
            Spacer(modifier = Modifier.height(16.dp))

            Divider()
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.weight),
                style = MaterialTheme.typography.caption,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${component.weight `in` grams} ${stringResource(R.string.unit_gram)}",
                style = MaterialTheme.typography.body1,
            )
            Spacer(modifier = Modifier.height(8.dp))

            Divider()
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.length),
                style = MaterialTheme.typography.caption,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${component.size.length `in` millimeters} " +
                        stringResource(R.string.unit_millimeter),
                style = MaterialTheme.typography.body1,
            )
            Spacer(modifier = Modifier.height(8.dp))

            Divider()
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.width),
                style = MaterialTheme.typography.caption,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${component.size.width `in` millimeters} " +
                        stringResource(R.string.unit_millimeter),
                style = MaterialTheme.typography.body1,
            )
            Spacer(modifier = Modifier.height(8.dp))

            Divider()
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.height),
                style = MaterialTheme.typography.caption,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${component.size.height `in` millimeters} " +
                        stringResource(R.string.unit_millimeter),
                style = MaterialTheme.typography.body1,
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
private fun ComponentDetailsScreenPreview() {
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
        Surface {
            ComponentDetailsScreen(component)
        }
    }
}

package io.github.tuguzt.pcbuilder.view.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.ComponentData
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.view.theme.PCBuilderTheme
import io.nacular.measured.units.Length
import io.nacular.measured.units.Mass
import io.nacular.measured.units.times

/**
 * Application screen with information about provided [component].
 */
@Composable
fun ComponentDetailsScreen(component: Component) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Name: ${component.name}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Description: ${component.description}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Weight: ${component.weight}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Length: ${component.size.length}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Width: ${component.size.width}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Height: ${component.size.height}")
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
            weight = 1 * Mass.kilograms,
            size = Size(
                length = 242 * Length.millimeters,
                width = 112 * Length.millimeters,
                height = 40 * Length.millimeters,
            ),
        )
        Surface {
            ComponentDetailsScreen(component)
        }
    }
}

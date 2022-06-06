package io.github.tuguzt.pcbuilder.presentation.view.root.main.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.domain.model.component.ThermalDesignPower
import io.github.tuguzt.pcbuilder.domain.model.component.Weight
import io.github.tuguzt.pcbuilder.domain.model.component.data.GpuChipsetData
import io.github.tuguzt.pcbuilder.domain.model.component.data.GpuData
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
import io.github.tuguzt.pcbuilder.domain.model.component.gpu.*
import io.github.tuguzt.pcbuilder.domain.model.units.hertz
import io.github.tuguzt.pcbuilder.domain.model.units.watt
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.theme.PCBuilderTheme
import io.nacular.measured.units.BinarySize
import io.nacular.measured.units.Length.Companion.millimeters
import io.nacular.measured.units.Mass.Companion.kilograms
import io.nacular.measured.units.times

/**
 * [Card] with data of the provided [component] and optional image provided by [painter].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponentItem(
    component: Component,
    isFavorite: Boolean,
    onFavoriteClick: (Boolean) -> Unit,
    painter: Painter? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    imageShape: Shape = MaterialTheme.shapes.medium,
    onClick: () -> Unit,
) {
    ElevatedCard(shape = shape, onClick = onClick) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                modifier = Modifier
                    .size(128.dp)
                    .clip(imageShape)
                    .run {
                        when (painter) {
                            null -> placeholder(
                                visible = true,
                                highlight = PlaceholderHighlight.fade(),
                            )
                            else -> this
                        }
                    },
                painter = painter ?: ColorPainter(Color.Transparent),
                contentDescription = painter?.let { stringResource(R.string.component_picture) },
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                DisableSelection {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(0.8f),
                            text = component.name,
                            maxLines = 2,
                            style = MaterialTheme.typography.titleMedium,
                            overflow = TextOverflow.Ellipsis,
                        )
                        IconButton(onClick = { onFavoriteClick(!isFavorite) }) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Rounded.Star else Icons.Rounded.StarOutline,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary,
                            )
                        }
                    }
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
        val component = GpuData(
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
            imageUri = null,
            isFavorite = false,
            `interface` = GpuInterface.AGP,
            chipset = GpuChipsetData("hehe boi"),
            coreClockRate = GpuClockRate(0 * hertz),
            boostClockRate = GpuClockRate(0 * hertz),
            memoryType = GpuMemoryType.GDDR6X,
            memoryCapacity = GpuMemoryCapacity(1 * BinarySize.gigabytes),
            multiSupport = null,
            frameSyncType = null,
            thermalDesignPower = ThermalDesignPower(0 * watt),
            ports = GpuPorts(
                dviCount = 0u,
                hdmiCount = 0u,
                miniHdmiCount = 0u,
                displayPortCount = 0u,
                miniDisplayPortCount = 0u,
            ),
            expansionSlotWidth = 0u,
            cooling = GpuCooling(0u, GpuCooling.Radiator.R360),
            externalPower = GpuExternalPower(
                pciExpress6pinCount = 0u,
                pciExpress8pinCount = 0u,
                pciExpress12pinCount = 0u,
                pciExpress16pinCount = 0u,
            ),
        )
        Surface {
            ComponentItem(
                component = component,
                painter = painterResource(R.drawable.ic_launcher_background),
                isFavorite = false,
                onFavoriteClick = {},
                onClick = {},
            )
        }
    }
}

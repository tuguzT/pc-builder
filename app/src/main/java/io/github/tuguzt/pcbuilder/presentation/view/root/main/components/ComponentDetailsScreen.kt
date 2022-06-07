package io.github.tuguzt.pcbuilder.presentation.view.root.main.components

import android.content.res.Configuration
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeveloperBoard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.domain.model.component.ThermalDesignPower
import io.github.tuguzt.pcbuilder.domain.model.component.Weight
import io.github.tuguzt.pcbuilder.domain.model.component.data.*
import io.github.tuguzt.pcbuilder.domain.model.component.gpu.*
import io.github.tuguzt.pcbuilder.domain.model.units.hertz
import io.github.tuguzt.pcbuilder.domain.model.units.watt
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.root.main.components.properties.CaseProperties
import io.github.tuguzt.pcbuilder.presentation.view.root.main.components.properties.ComponentProperties
import io.github.tuguzt.pcbuilder.presentation.view.root.main.components.properties.MotherboardProperties
import io.github.tuguzt.pcbuilder.presentation.view.theme.PCBuilderTheme
import io.nacular.measured.units.BinarySize.Companion.gigabytes
import io.nacular.measured.units.Length.Companion.millimeters
import io.nacular.measured.units.Mass.Companion.kilograms
import io.nacular.measured.units.times

/**
 * Application screen with information about provided [component].
 */
@Composable
fun ComponentDetailsScreen(
    component: Component,
    scrollState: ScrollState = rememberScrollState(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        ComponentImage(component)

        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
        ) {
            ComponentProperties(component)

            if (component is CaseData) CaseProperties(component)
            if (component is MotherboardData) MotherboardProperties(component)
        }
    }
}

@Composable
private fun ComponentImage(component: Component) {
    val imageShape = MaterialTheme.shapes.medium.copy(
        topStart = ZeroCornerSize,
        topEnd = ZeroCornerSize,
    )
    var imageState: AsyncImagePainter.State by remember {
        mutableStateOf(AsyncImagePainter.State.Empty)
    }
    if (imageState !is AsyncImagePainter.State.Error && component.imageUri != null) {
        AsyncImage(
            model = component.imageUri,
            contentDescription = stringResource(R.string.component_picture),
            modifier = Modifier
                .heightIn(min = 240.dp)
                .fillMaxWidth()
                .clip(imageShape)
                .placeholder(
                    visible = imageState is AsyncImagePainter.State.Loading,
                    highlight = PlaceholderHighlight.fade(),
                ),
            onState = { imageState = it },
        )
    } else {
        Surface(tonalElevation = 2.dp, shape = imageShape) {
            Icon(
                modifier = Modifier
                    .heightIn(min = 240.dp)
                    .fillMaxWidth(),
                imageVector = Icons.Rounded.DeveloperBoard,
                contentDescription = stringResource(R.string.image_not_loaded),
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
            memoryCapacity = GpuMemoryCapacity(1 * gigabytes),
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
            ComponentDetailsScreen(component)
        }
    }
}

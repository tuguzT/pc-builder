package io.github.tuguzt.pcbuilder.presentation.view.root.main.components

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.domain.model.component.ThermalDesignPower
import io.github.tuguzt.pcbuilder.domain.model.component.Weight
import io.github.tuguzt.pcbuilder.domain.model.component.data.GpuChipsetData
import io.github.tuguzt.pcbuilder.domain.model.component.data.GpuData
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
import io.github.tuguzt.pcbuilder.domain.model.component.data.PolymorphicComponent
import io.github.tuguzt.pcbuilder.domain.model.component.gpu.*
import io.github.tuguzt.pcbuilder.domain.model.units.hertz
import io.github.tuguzt.pcbuilder.domain.model.units.watt
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.theme.PCBuilderTheme
import io.nacular.measured.units.BinarySize
import io.nacular.measured.units.Length
import io.nacular.measured.units.Mass
import io.nacular.measured.units.times

@OptIn(
    ExperimentalPagerApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class,
)
@Composable
fun ComponentCompareScreen(
    firstComponent: PolymorphicComponent?,
    secondComponent: PolymorphicComponent?,
    onFirstComponentCleared: () -> Unit,
    onSecondComponentCleared: () -> Unit,
    onFirstComponentChoose: () -> Unit,
    onSecondComponentChoose: () -> Unit,
    scrollState: ScrollState = rememberScrollState(),
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val pagerState = rememberPagerState()
        val (onClear, onChoose) = remember(pagerState.currentPage) {
            when (pagerState.currentPage) {
                0 -> onFirstComponentCleared to onFirstComponentChoose
                else -> onSecondComponentCleared to onSecondComponentChoose
            }
        }

        HorizontalPager(modifier = Modifier.fillMaxSize(), count = 2, state = pagerState) { page ->
            @Composable
            fun PageContent(component: PolymorphicComponent?) {
                AnimatedContent(targetState = component) { targetState ->
                    when (targetState) {
                        null -> Button(onClick = onChoose) {
                            Text("Choose component")
                        }
                        else -> ComponentDetailsScreen(targetState, scrollState)
                    }
                }
            }
            when (page) {
                0 -> PageContent(component = firstComponent)
                else -> PageContent(component = secondComponent)
            }
        }

        val currentComponent = when (pagerState.currentPage) {
            0 -> firstComponent
            else -> secondComponent
        }
        AnimatedVisibility(
            visible = currentComponent != null,
            enter = fadeIn() + scaleIn(),
            exit = scaleOut() + fadeOut(),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp),
        ) {
            FilledTonalIconButton(onClick = onClear) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = stringResource(R.string.clear_selection),
                )
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
private fun ComponentCompareScreenPreview() {
    PCBuilderTheme {
        val component = GpuData(
            name = "NVIDIA GeForce RTX 3050",
            description = "The RTX 3050 is built on Ampere architecture and uses 8GB" +
                    " of GDDR6 VRAM. This is the same memory found in the RTX 3060 Ti." +
                    " The card has 2,560 CUDA cores with a base clock of 1.55 GHz and" +
                    " a boost clock of 1.78 GHz. It also has a 128-bit memory interface width" +
                    " seen in GPUs targeting 1080p.",
            weight = Weight(1 * Mass.kilograms),
            size = Size(
                length = 242 * Length.millimeters,
                width = 112 * Length.millimeters,
                height = 40 * Length.millimeters,
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
            ComponentCompareScreen(
                firstComponent = component,
                secondComponent = null,
                onFirstComponentCleared = {},
                onSecondComponentCleared = {},
                onFirstComponentChoose = {},
                onSecondComponentChoose = {},
            )
        }
    }
}

package io.github.tuguzt.pcbuilder.presentation.view.root.main.components.properties

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.tuguzt.pcbuilder.domain.model.component.gpu.GpuMultiSupport
import io.github.tuguzt.pcbuilder.domain.model.component.memory.MemoryECCType
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.Motherboard
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardCpuSocket
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.asMeasure
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.toUInt
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.utils.SimpleProperty
import io.github.tuguzt.pcbuilder.presentation.view.utils.round
import io.nacular.measured.units.BinarySize

@Composable
fun MotherboardProperties(
    motherboard: Motherboard,
    modifier: Modifier = Modifier,
) = Column(modifier) {
    Divider()
    SimpleProperty(
        name = stringResource(R.string.motherboard_form_factor),
        value = motherboard.formFactor.toString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.motherboard_chipset),
        value = motherboard.chipset.toString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.cpu_socket),
        value = when (val socket = motherboard.cpuSocket) {
            is MotherboardCpuSocket.Standard -> socket.socket.toString()
            is MotherboardCpuSocket.Integrated -> "${stringResource(R.string.integrated)} $socket"
        },
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.memory_type),
        value = motherboard.memoryType.toString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.memory_ecc_support),
        value = when (motherboard.memoryECCType) {
            MemoryECCType.ECC -> stringResource(R.string.yes)
            MemoryECCType.NonECC -> stringResource(R.string.no)
        },
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.motherboard_max_memory_amount),
        value = run {
            val value = motherboard.memoryAmount.asMeasure() `in` BinarySize.gigabytes
            "${value.round(3)} ${stringResource(R.string.unit_gigabytes)}"
        },
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.motherboard_memory_slot_count),
        value = motherboard.memorySlotCount.toUInt().toString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.gpu_crossfire_x_support),
        value = when (val support = motherboard.multiGpuSupport) {
            is GpuMultiSupport.CrossFireX -> stringResource(
                R.string.gpu_multi_support_with_ways,
                support.wayCount.count.toInt(),
            )
            else -> stringResource(R.string.no)
        },
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.gpu_sli_support),
        value = when (val support = motherboard.multiGpuSupport) {
            is GpuMultiSupport.SLI -> stringResource(
                R.string.gpu_multi_support_with_ways,
                support.wayCount.count.toInt(),
            )
            else -> stringResource(R.string.no)
        },
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.motherboard_pcie_x16_slots),
        value = motherboard.slots.pciExpressX16Count.toString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.motherboard_pcie_x8_slots),
        value = motherboard.slots.pciExpressX8Count.toString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.motherboard_pcie_x4_slots),
        value = motherboard.slots.pciExpressX4Count.toString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.motherboard_pcie_x1_slots),
        value = motherboard.slots.pciExpressX1Count.toString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.motherboard_pci_slots),
        value = motherboard.slots.pciCount.toString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.motherboard_m2_slots),
        value = motherboard.slots.m2Count.toString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.motherboard_m_sata_slots),
        value = motherboard.slots.mSataCount.toString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.motherboard_sata_3gbps_ports),
        value = motherboard.ports.sata3GBpSecCount.toString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.motherboard_sata_6gbps_ports),
        value = motherboard.ports.sata6GBpSecCount.toString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.motherboard_usb2_headers),
        value = motherboard.usbHeaders.usb2HeaderCount.toString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.motherboard_usb_3_2_gen1_headers),
        value = motherboard.usbHeaders.usb3gen1HeaderCount.toString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.motherboard_usb_3_2_gen2_headers),
        value = motherboard.usbHeaders.usb3gen2HeaderCount.toString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.motherboard_usb_3_2_gen2x2_headers),
        value = motherboard.usbHeaders.usb3gen2x2HeaderCount.toString(),
    )
}

package io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.builds

import io.github.tuguzt.pcbuilder.domain.interactor.randomNanoId
import io.github.tuguzt.pcbuilder.domain.model.build.BuildData
import io.github.tuguzt.pcbuilder.domain.model.component.data.*
import io.github.tuguzt.pcbuilder.presentation.viewmodel.MessageKind
import io.github.tuguzt.pcbuilder.presentation.viewmodel.MessageState
import io.github.tuguzt.pcbuilder.presentation.viewmodel.UserMessage

data class BuildsState(
    val builds: List<BuildData> = listOf(),
    val isUpdating: Boolean = true,
    override val userMessages: List<UserMessage<out BuildsMessageKind>> = listOf(),
) : MessageState<BuildsMessageKind>

enum class BuildsMessageKind : MessageKind {
    BuildSaved,
    BuildDeleted,
    UnknownError,
}

data class AddBuildState(
    val name: String = "",
    val case: CaseData? = null,
    val centralProcessingUnit: CpuData? = null,
    val cooler: CoolerData? = null,
    val graphicsProcessingUnit: List<GpuData> = listOf(),
    val memory: List<MemoryData> = listOf(),
    val monitor: List<MonitorData> = listOf(),
    val motherboard: MotherboardData? = null,
    val powerSupplyUnit: PsuData? = null,
    val storage: List<StorageData> = listOf(),
)

inline val AddBuildState.isValid: Boolean
    get() = name.isNotBlank()

fun AddBuildState.toData() = BuildData(
    id = randomNanoId(),
    name = name,
    case = case,
    cooler = cooler,
    centralProcessingUnit = centralProcessingUnit,
    graphicsProcessingUnit = graphicsProcessingUnit,
    memory = memory,
    monitor = monitor,
    motherboard = motherboard,
    powerSupplyUnit = powerSupplyUnit,
    storage = storage,
)

data class EditBuildState(
    val name: String = "",
    val case: CaseData? = null,
    val centralProcessingUnit: CpuData? = null,
    val cooler: CoolerData? = null,
    val graphicsProcessingUnit: List<GpuData> = listOf(),
    val memory: List<MemoryData> = listOf(),
    val monitor: List<MonitorData> = listOf(),
    val motherboard: MotherboardData? = null,
    val powerSupplyUnit: PsuData? = null,
    val storage: List<StorageData> = listOf(),
    val prevBuildData: BuildData? = null,
)

inline val EditBuildState.isValid: Boolean
    get() = name.isNotBlank() && toData() != prevBuildData

fun EditBuildState.toData() = BuildData(
    id = prevBuildData?.id ?: randomNanoId(),
    name = name,
    case = case,
    cooler = cooler,
    centralProcessingUnit = centralProcessingUnit,
    graphicsProcessingUnit = graphicsProcessingUnit,
    memory = memory,
    monitor = monitor,
    motherboard = motherboard,
    powerSupplyUnit = powerSupplyUnit,
    storage = storage,
)

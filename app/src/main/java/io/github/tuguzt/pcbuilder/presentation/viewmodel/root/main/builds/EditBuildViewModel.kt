package io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.builds

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.pcbuilder.domain.model.build.BuildData
import io.github.tuguzt.pcbuilder.domain.model.component.data.*
import mu.KotlinLogging
import javax.inject.Inject

@HiltViewModel
class EditBuildViewModel @Inject constructor() : ViewModel() {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private var _uiState by mutableStateOf(EditBuildState())
    val uiState get() = _uiState

    fun updateName(name: String) {
        _uiState = uiState.copy(name = name)
    }

    fun updateCase(case: CaseData?) {
        _uiState = uiState.copy(case = case)
    }

    fun updateCentralProcessingUnit(centralProcessingUnit: CpuData?) {
        _uiState = uiState.copy(centralProcessingUnit = centralProcessingUnit)
    }

    fun updateCooler(cooler: CoolerData?) {
        _uiState = uiState.copy(cooler = cooler)
    }

    fun updateGraphicsProcessingUnit(graphicsProcessingUnit: List<GpuData>) {
        _uiState = uiState.copy(graphicsProcessingUnit = graphicsProcessingUnit)
    }

    fun updateMemory(memory: List<MemoryData>) {
        _uiState = uiState.copy(memory = memory)
    }

    fun updateMonitor(monitor: List<MonitorData>) {
        _uiState = uiState.copy(monitor = monitor)
    }

    fun updateMotherboard(motherboard: MotherboardData?) {
        _uiState = uiState.copy(motherboard = motherboard)
    }

    fun updatePowerSupplyUnit(powerSupplyUnit: PsuData?) {
        _uiState = uiState.copy(powerSupplyUnit = powerSupplyUnit)
    }

    fun updateStorage(storage: List<StorageData>) {
        _uiState = uiState.copy(storage = storage)
    }

    fun updatePrevBuildData(prevBuildData: BuildData) {
        _uiState = uiState.copy(
            prevBuildData = prevBuildData,
            name = prevBuildData.name,
            case = prevBuildData.case,
            centralProcessingUnit = prevBuildData.centralProcessingUnit,
            cooler = prevBuildData.cooler,
            graphicsProcessingUnit = prevBuildData.graphicsProcessingUnit,
            memory = prevBuildData.memory,
            monitor = prevBuildData.monitor,
            motherboard = prevBuildData.motherboard,
            powerSupplyUnit = prevBuildData.powerSupplyUnit,
            storage = prevBuildData.storage,
        )
    }
}

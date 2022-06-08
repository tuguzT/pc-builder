package io.github.tuguzt.pcbuilder.presentation.view.root.main.components.properties

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.tuguzt.pcbuilder.domain.model.component.cases.Case
import io.github.tuguzt.pcbuilder.domain.model.component.cases.CasePowerSupplyShroud
import io.github.tuguzt.pcbuilder.domain.model.component.cases.asMeasure
import io.github.tuguzt.pcbuilder.domain.model.units.watt
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.utils.SimpleProperty
import io.github.tuguzt.pcbuilder.presentation.view.utils.round

@Composable
fun CaseProperties(case: Case, modifier: Modifier = Modifier) = Column(modifier) {
    Divider()
    SimpleProperty(
        name = stringResource(R.string.case_type),
        value = case.caseType.toString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.case_external_5_25_bays),
        value = case.driveBays.external5_25_count.toString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.case_external_3_5_bays),
        value = case.driveBays.external3_5_count.toString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.case_internal_3_5_bays),
        value = case.driveBays.internal3_5_count.toString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.case_internal_2_5_bays),
        value = case.driveBays.internal2_5_count.toString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.case_full_height_expansion_slots),
        value = case.expansionSlots.fullHeightCount.toString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.case_half_height_expansion_slots),
        value = case.expansionSlots.halfHeightCount.toString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.motherboard_form_factors),
        value = case.motherboardFormFactors.joinToString(),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.case_power_supply),
        value = case.powerSupply?.let {
            val value = it.asMeasure() `in` watt
            "${value.round(3)} ${stringResource(R.string.unit_watt)}"
        } ?: stringResource(R.string.none),
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.case_power_supply_shroud),
        value = when (case.powerSupplyShroud) {
            CasePowerSupplyShroud.Shroud -> stringResource(R.string.shroud)
            CasePowerSupplyShroud.NonShroud -> stringResource(R.string.non_shroud)
        },
    )
    Divider()
    SimpleProperty(
        name = stringResource(R.string.case_side_panel_window),
        value = case.sidePanelWindow?.toString() ?: stringResource(R.string.none),
    )
}

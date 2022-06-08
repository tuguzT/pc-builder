package io.github.tuguzt.pcbuilder.presentation.view.root.main.builds

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.tuguzt.pcbuilder.domain.model.build.BuildData
import io.github.tuguzt.pcbuilder.domain.model.component.data.CaseData
import io.github.tuguzt.pcbuilder.domain.model.component.data.MotherboardData
import io.github.tuguzt.pcbuilder.domain.model.component.data.PolymorphicComponent
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.MainViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.builds.AddBuildViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.builds.isValid
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components.ComponentsViewModel

@Composable
fun AddBuildScreen(
    onAdd: (BuildData) -> Unit,
    mainViewModel: MainViewModel,
    componentsViewModel: ComponentsViewModel,
    addBuildViewModel: AddBuildViewModel = hiltViewModel(),
) {
    val focusManager = LocalFocusManager.current
    val title = stringResource(R.string.add_build)
    SideEffect {
        mainViewModel.updateTitle(title)
    }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = focusManager::clearFocus,
                ),
        ) {
            OutlinedTextField(
                value = addBuildViewModel.uiState.name,
                onValueChange = addBuildViewModel::updateName,
                label = { Text(stringResource(R.string.name)) },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(8.dp))
            ComponentExposedDropdown(
                items = remember(componentsViewModel.uiState) {
                    componentsViewModel.uiState.components.filterIsInstance<CaseData>()
                },
                label = stringResource(R.string.case_label),
                onItemChoose = { addBuildViewModel.updateCase(it) },
            )
            Spacer(modifier = Modifier.height(8.dp))
            ComponentExposedDropdown(
                items = remember(componentsViewModel.uiState) {
                    componentsViewModel.uiState.components.filterIsInstance<MotherboardData>()
                },
                label = stringResource(R.string.motherboard),
                onItemChoose = { addBuildViewModel.updateMotherboard(it) },
            )
            Spacer(
                modifier = Modifier
                    .height(8.dp)
                    .weight(1f),
            )
            Button(
                onClick = {
                    val build = addBuildViewModel.currentBuild()
                    onAdd(build)
                },
                modifier = Modifier.align(Alignment.End),
                enabled = addBuildViewModel.uiState.isValid,
            ) {
                Text(stringResource(id = R.string.add_build))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : PolymorphicComponent> ComponentExposedDropdown(
    items: List<T>,
    label: String,
    onItemChoose: (T?) -> Unit,
) {
    val options by remember(items) {
        derivedStateOf {
            buildList {
                add(null)
                addAll(items)
            }
        }
    }
    var expanded by remember { mutableStateOf(false) }

    val none = stringResource(R.string.none)
    var chosenItemName by remember { mutableStateOf(none) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        OutlinedTextField(
            value = chosenItemName,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            label = { Text(label) },
            readOnly = true,
            maxLines = 1,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = selectionOption?.name ?: none,
                            maxLines = 1,
                        )
                    },
                    onClick = {
                        val item = items.firstOrNull { selectionOption == it }
                        chosenItemName = item?.name ?: none
                        onItemChoose(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

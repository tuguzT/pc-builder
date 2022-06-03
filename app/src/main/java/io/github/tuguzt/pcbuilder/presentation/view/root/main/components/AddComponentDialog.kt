package io.github.tuguzt.pcbuilder.presentation.view.root.main.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.pcbuilder.domain.interactor.randomNanoId
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.domain.model.component.Weight
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.theme.PCBuilderTheme
import io.github.tuguzt.pcbuilder.presentation.view.utils.HelperOutlinedTextField
import io.nacular.measured.units.Length.Companion.millimeters
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.times

/**
 * Application dialog for manual [component][ComponentData] creation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddComponentDialog(
    modifier: Modifier = Modifier,
    onAddComponent: (ComponentData) -> Unit,
    onNavigateUp: () -> Unit,
) {
    var name by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }
    var length by rememberSaveable { mutableStateOf("") }
    var width by rememberSaveable { mutableStateOf("") }
    var height by rememberSaveable { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = focusManager::clearFocus,
        ),
        topBar = {
            SmallTopAppBar(
                title = { Text(text = stringResource(R.string.add_new_component)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(R.string.name)) },
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(R.string.description)) },
                )
                Spacer(modifier = Modifier.height(16.dp))

                HelperOutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(R.string.weight)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    helperText = stringResource(R.string.weight_in_g),
                )
                Spacer(modifier = Modifier.height(16.dp))

                HelperOutlinedTextField(
                    value = length,
                    onValueChange = { length = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(R.string.length)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    helperText = stringResource(R.string.length_in_mm),
                )
                Spacer(modifier = Modifier.height(16.dp))

                HelperOutlinedTextField(
                    value = width,
                    onValueChange = { width = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(R.string.width)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    helperText = stringResource(R.string.width_in_mm),
                )
                Spacer(modifier = Modifier.height(16.dp))

                HelperOutlinedTextField(
                    value = height,
                    onValueChange = { height = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(R.string.height)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    helperText = stringResource(R.string.height_in_mm),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = {
                    val component = ComponentData(
                        id = randomNanoId(),
                        name = name,
                        description = description,
                        weight = Weight(weight.toDouble() * grams),
                        size = Size(
                            length = length.toDouble() * millimeters,
                            width = width.toDouble() * millimeters,
                            height = height.toDouble() * millimeters,
                        ),
                        manufacturer = ManufacturerData(
                            name = "Example",
                            description = "Hello World",
                        ),
                    )
                    onAddComponent(component)
                },
                enabled = kotlin.run {
                    weight.toDoubleOrNull() ?: return@run false
                    length.toDoubleOrNull() ?: return@run false
                    width.toDoubleOrNull() ?: return@run false
                    height.toDoubleOrNull() ?: return@run false
                    name.isNotEmpty() and description.isNotEmpty()
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                )
                Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                Text(text = stringResource(R.string.add_component))
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
private fun AddComponentDialogPreview() {
    PCBuilderTheme {
        AddComponentDialog(
            modifier = Modifier.fillMaxSize(),
            onAddComponent = {},
            onNavigateUp = {},
        )
    }
}

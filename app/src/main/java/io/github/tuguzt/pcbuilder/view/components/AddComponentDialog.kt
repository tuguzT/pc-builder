package io.github.tuguzt.pcbuilder.view.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.domain.interactor.randomNanoId
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.model.component.ComponentData
import io.github.tuguzt.pcbuilder.view.theme.PCBuilderTheme
import io.nacular.measured.units.Length.Companion.millimeters
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.times

@Composable
fun AddComponentDialog(
    onDismissRequest: () -> Unit,
    onAddComponent: (ComponentData) -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        AddComponentDialogContent(onAddComponent)
    }
}

@Composable
private fun AddComponentDialogContent(onAddComponent: (ComponentData) -> Unit) {
    var name by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }
    var length by rememberSaveable { mutableStateOf("") }
    var width by rememberSaveable { mutableStateOf("") }
    var height by rememberSaveable { mutableStateOf("") }

    Surface(shape = RoundedCornerShape(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.add_new_component),
                style = MaterialTheme.typography.h5,
            )
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(weight = 1f, fill = false)
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.name)) },
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(R.string.description)) }
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text(stringResource(R.string.weight)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Text(
                    text = stringResource(R.string.weight_in_g),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = length,
                    onValueChange = { length = it },
                    label = { Text(stringResource(R.string.length)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Text(
                    text = stringResource(R.string.length_in_mm),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = width,
                    onValueChange = { width = it },
                    label = { Text(stringResource(R.string.width)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Text(
                    text = stringResource(R.string.width_in_mm),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = height,
                    onValueChange = { height = it },
                    label = { Text(stringResource(R.string.height)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Text(
                    text = stringResource(R.string.height_in_mm),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val component = ComponentData(
                        id = randomNanoId(),
                        name = name,
                        description = description,
                        weight = weight.toDouble() * grams,
                        size = Size(
                            length = length.toDouble() * millimeters,
                            width = width.toDouble() * millimeters,
                            height = height.toDouble() * millimeters,
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
        AddComponentDialogContent(onAddComponent = {})
    }
}

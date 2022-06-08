package io.github.tuguzt.pcbuilder.presentation.view.root.main.builds

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.tuguzt.pcbuilder.domain.model.component.data.PolymorphicComponent
import io.github.tuguzt.pcbuilder.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : PolymorphicComponent> ComponentExposedDropdown(
    items: List<T>,
    label: String,
    currentItem: T?,
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

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        OutlinedTextField(
            value = currentItem?.name ?: none,
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
                        onItemChoose(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

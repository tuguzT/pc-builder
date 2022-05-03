package io.github.tuguzt.pcbuilder.view.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.view.theme.PCBuilderTheme
import kotlinx.coroutines.launch

@Composable
fun ComponentsScreen() {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    var showAddComponentDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddComponentDialog = true }) {
                Icon(Icons.Rounded.Add, contentDescription = stringResource(R.string.add_component))
            }
        }
    ) {
        val componentAddedMessage = stringResource(R.string.component_added)

        if (showAddComponentDialog) {
            AddComponentDialog(
                onDismissRequest = {
                    showAddComponentDialog = false
                },
                onAddComponent = {
                    showAddComponentDialog = false
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(message = componentAddedMessage)
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ComponentsScreenPreview() {
    PCBuilderTheme {
        ComponentsScreen()
    }
}

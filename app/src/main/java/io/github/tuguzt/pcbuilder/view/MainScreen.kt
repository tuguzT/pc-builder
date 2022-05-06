package io.github.tuguzt.pcbuilder.view

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.view.account.AccountScreen
import io.github.tuguzt.pcbuilder.view.builds.BuildsScreen
import io.github.tuguzt.pcbuilder.view.components.ComponentsScreen
import io.github.tuguzt.pcbuilder.view.navigation.MainScreenDestinations.*
import io.github.tuguzt.pcbuilder.view.theme.PCBuilderTheme
import io.github.tuguzt.pcbuilder.viewmodel.AccountViewModel

/**
 * Main screen of the application.
 */
@Composable
fun MainScreen(
    onSignOut: () -> Unit,
    accountViewModel: AccountViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
) {
    var showSearch by rememberSaveable { mutableStateOf(true) }

    Scaffold(
        topBar = {
            MainScreenTopAppBar(
                showSearch = showSearch,
                onSearchClick = { /* TODO */ },
            )
        },
        bottomBar = {
            DestinationsBottomNavigation(
                navController = navController,
                destinations = listOf(Components, Builds, Account),
            )
        },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Components.route,
            modifier = Modifier.padding(padding),
        ) {
            composable(Components.route) {
                showSearch = true
                ComponentsScreen()
            }
            composable(Builds.route) {
                showSearch = false
                BuildsScreen()
            }
            composable(Account.route) account@ {
                showSearch = false

                val user = accountViewModel.currentUser ?: kotlin.run {
                    onSignOut()
                    return@account
                }
                val context = LocalContext.current
                val toastText = stringResource(R.string.signed_out_success)
                AccountScreen(
                    user = user,
                    onSignOut = {
                        showToast(context, toastText, ToastDuration.Short)
                        onSignOut()
                    },
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
private fun MainScreenPreview() {
    PCBuilderTheme {
        MainScreen(onSignOut = {})
    }
}

@Composable
private fun MainScreenTopAppBar(showSearch: Boolean, onSearchClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        actions = {
            AnimatedVisibility(visible = showSearch) {
                IconButton(onClick = onSearchClick) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = stringResource(R.string.search_components),
                    )
                }
            }
        }
    )
}
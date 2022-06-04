package io.github.tuguzt.pcbuilder.presentation.view.root.main

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.ToastDuration
import io.github.tuguzt.pcbuilder.presentation.view.navigation.MainScreenDestinations.*
import io.github.tuguzt.pcbuilder.presentation.view.root.main.account.AccountScreen
import io.github.tuguzt.pcbuilder.presentation.view.root.main.builds.BuildsScreen
import io.github.tuguzt.pcbuilder.presentation.view.root.main.components.ComponentsScreen
import io.github.tuguzt.pcbuilder.presentation.view.root.main.learn.LearnScreen
import io.github.tuguzt.pcbuilder.presentation.view.showToast
import io.github.tuguzt.pcbuilder.presentation.view.theme.PCBuilderTheme
import io.github.tuguzt.pcbuilder.presentation.view.utils.DestinationsNavigationBar
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.account.AccountViewModel

/**
 * Main screen of the application.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    accountViewModel: AccountViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val appName = stringResource(R.string.app_name)

    var titleText by rememberSaveable { mutableStateOf(appName) }
    var showSearch by rememberSaveable { mutableStateOf(true) }

    Scaffold(
        topBar = {
            MainScreenTopAppBar(
                titleText = titleText,
                showSearch = showSearch,
                onSearchClick = { /* TODO */ },
            )
        },
        bottomBar = {
            DestinationsNavigationBar(
                navController = navController,
                destinations = listOf(Components, Builds, Learn, Account),
                onDestinationNavigate = { destination ->
                    showSearch = when (destination) {
                        Components -> true
                        else -> false
                    }
                }
            )
        },
    ) { padding ->
        val onTitleChanged: (String) -> Unit = { titleText = it }

        NavHost(
            navController = navController,
            startDestination = Components.route,
            modifier = Modifier.padding(padding),
        ) {
            composable(Components.route) {
                ComponentsScreen(onTitleChanged)
            }
            composable(Builds.route) {
                BuildsScreen(onTitleChanged)
            }
            composable(Learn.route) {
                LearnScreen(onTitleChanged)
            }
            composable(Account.route) account@{
                val user = accountViewModel.uiState.currentUser ?: return@account

                val context = LocalContext.current
                val toastText = stringResource(R.string.signed_out_success)
                AccountScreen(
                    user = user,
                    onTitleChanged = onTitleChanged,
                    onSignOut = {
                        showToast(context, toastText, ToastDuration.Short)
                        accountViewModel.signOut()
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
        MainScreen()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun MainScreenTopAppBar(
    titleText: String,
    showSearch: Boolean,
    onSearchClick: () -> Unit,
) {
    Surface(tonalElevation = 2.dp) {
        CenterAlignedTopAppBar(
            title = {
                AnimatedContent(targetState = titleText) { text -> Text(text) }
            },
            actions = {
                AnimatedVisibility(visible = showSearch) {
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = stringResource(R.string.search_components),
                        )
                    }
                }
            },
        )
    }
}
package io.github.tuguzt.pcbuilder.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.view.MainScreenDestinations.*
import io.github.tuguzt.pcbuilder.view.components.ComponentsScreen
import io.github.tuguzt.pcbuilder.view.theme.PCBuilderTheme

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    val showSearch by rememberSaveable { mutableStateOf(true) }

    Scaffold(
        topBar = { TopBar(showSearch = showSearch, onSearchClick = {}) },
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Components.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(Components.route) { ComponentsScreen() }
            composable(Builds.route) { Text(Builds.text) }
            composable(Account.route) { Text(Account.text) }
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
    PCBuilderTheme { MainScreen() }
}

@Composable
private fun TopBar(showSearch: Boolean, onSearchClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        actions = {
            if (showSearch) {
                IconButton(onClick = onSearchClick) {
                    Icon(Icons.Rounded.Search, stringResource(R.string.search_components))
                }
            }
        }
    )
}

@Composable
private fun BottomBar(navController: NavHostController) {
    val items = listOf(Components, Builds, Account)

    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { destination ->
            BottomNavigationItem(
                icon = { Icon(destination.imageVector, destination.text) },
                label = { Text(text = destination.text) },
                selected = currentDestination
                    ?.hierarchy
                    ?.any { it.route == destination.route } == true,
                onClick = {
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

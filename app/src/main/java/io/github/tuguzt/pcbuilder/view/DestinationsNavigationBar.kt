package io.github.tuguzt.pcbuilder.view

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.view.navigation.BottomNavigationDestination
import io.github.tuguzt.pcbuilder.view.navigation.MainScreenDestinations.*
import io.github.tuguzt.pcbuilder.view.theme.PCBuilderTheme

/**
 * [NavigationBar] composable with configured [destinations].
 */
@Composable
fun DestinationsNavigationBar(
    navController: NavHostController,
    destinations: List<BottomNavigationDestination>,
    modifier: Modifier = Modifier,
    onDestinationNavigate: (BottomNavigationDestination) -> Unit = {},
) {
    NavigationBar(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        destinations.forEach { destination ->
            NavigationBarItem(
                icon = { Icon(destination.icon, destination.description) },
                label = { Text(text = destination.description) },
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
                    onDestinationNavigate(destination)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun DestinationsNavigationBarPreview() {
    PCBuilderTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = stringResource(R.string.app_name)) },
                )
            },
            bottomBar = {
                DestinationsNavigationBar(
                    navController = rememberNavController(),
                    destinations = listOf(Components, Builds, Account),
                )
            },
            content = {},
        )
    }
}

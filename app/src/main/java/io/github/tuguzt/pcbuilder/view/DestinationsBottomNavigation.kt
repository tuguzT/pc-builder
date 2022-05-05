package io.github.tuguzt.pcbuilder.view

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
 * [BottomNavigation] composable with configured [destinations].
 */
@Composable
fun DestinationsBottomNavigation(
    navController: NavHostController,
    destinations: List<BottomNavigationDestination>,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = BottomNavigationDefaults.Elevation,
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        destinations.forEach { destination ->
            BottomNavigationItem(
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
                }
            )
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun DestinationsBottomNavigationPreview() {
    PCBuilderTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.app_name)) },
                )
            },
            bottomBar = {
                DestinationsBottomNavigation(
                    navController = rememberNavController(),
                    destinations = listOf(Components, Builds, Account),
                )
            },
            content = {},
        )
    }
}

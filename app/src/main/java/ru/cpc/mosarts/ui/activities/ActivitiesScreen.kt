package ru.cpc.mosarts.ui.activities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import ru.cpc.mosarts.R
import ru.cpc.mosarts.ui.NavGraphs
import ru.cpc.mosarts.ui.activities.utils.BottomBarDestination
import ru.cpc.mosarts.ui.appCurrentDestinationAsState
import ru.cpc.mosarts.ui.startAppDestination
import ru.cpc.mosarts.ui.theme.BlueTextColor
import ru.cpc.mosarts.ui.theme.Grey

@Destination
@Composable
fun ActivitiesScreen() {
    val navController = rememberNavController()
    val currentDestination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination
    val title = BottomBarDestination.values().find { it.direction == currentDestination }
        ?.topBarTitle
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(id = title ?: R.string.app_name)) })
        },
        bottomBar = {
            BottomBar(
                navController
            )
        }
    ) {
        DestinationsNavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            navGraph = NavGraphs.main
        )
    }
}

@Composable
fun BottomBar(
    navController: NavController,
) {
    val currentDestination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    Column {
        BottomNavigation {
            BottomBarDestination.values().forEach { destination ->
                BottomNavigationItem(
                    selected = currentDestination == destination.direction,
                    onClick = {
                        navController.navigate(destination.direction.route) {
                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(destination.icon),
                            contentDescription = null
                        )
                    },
                    selectedContentColor = BlueTextColor,
                    unselectedContentColor = Grey
                )
            }
        }
    }
}

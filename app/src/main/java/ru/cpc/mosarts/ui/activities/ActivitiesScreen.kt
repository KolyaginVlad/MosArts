package ru.cpc.mosarts.ui.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import ru.cpc.mosarts.R
import ru.cpc.mosarts.ui.NavGraphs
import ru.cpc.mosarts.ui.activities.utils.BottomBarDestination
import ru.cpc.mosarts.ui.appCurrentDestinationAsState
import ru.cpc.mosarts.ui.startAppDestination

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
                        val boxModifier = if (currentDestination == destination.direction) {
                            Modifier
                                .background(
                                    color = MaterialTheme.colors.secondary.copy(alpha = 0.25f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(4.dp)
                        } else {
                            Modifier
                        }
                        Box(modifier = boxModifier) {
                            Icon(
                                painter = painterResource(destination.icon),
                                contentDescription = null
                            )
                        }
                    },
                    selectedContentColor = MaterialTheme.colors.onPrimary,
                    unselectedContentColor = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

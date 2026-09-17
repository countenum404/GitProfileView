package com.shabashov.gitprofileview.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shabashov.gitprofileview.presentation.finder.SearchProfileView
import com.shabashov.gitprofileview.presentation.finder.SearchProfileViewModel
import com.shabashov.gitprofileview.presentation.history.HistoryView
import com.shabashov.gitprofileview.presentation.history.HistoryViewModel
import com.shabashov.gitprofileview.presentation.settings.SettingsView
import com.shabashov.gitprofileview.presentation.settings.SettingsViewModel
import com.shabashov.gitprofileview.presentation.ui.theme.GitProfileViewTheme

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SearchScreen.route) {
        composable(Screen.SearchScreen.route) {
            val viewModel: SearchProfileViewModel = hiltViewModel()
            SearchProfileView(
                modifier = Modifier,
                viewModel = viewModel,
                onFloatingActionButtonClick = {
                    navController.navigate(Screen.ProfilesHistoryScreen.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onSettingsClicked = {
                    navController.navigate(Screen.SettingsScreen.route)  {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(Screen.ProfilesHistoryScreen.route) {
            val viewModel: HistoryViewModel = hiltViewModel()
            HistoryView(
                modifier = Modifier,
                viewModel = viewModel
            )
        }

        composable(Screen.SettingsScreen.route) {
            val viewModel: SettingsViewModel = hiltViewModel()
            SettingsView(
                modifier = Modifier,
                viewModel = viewModel
            )
        }
    }
}

sealed class Screen(val route: String) {
    data object SearchScreen: Screen(route = "search")
    data object ProfilesHistoryScreen: Screen(route = "history")
    data object SettingsScreen: Screen(route = "settings")
}

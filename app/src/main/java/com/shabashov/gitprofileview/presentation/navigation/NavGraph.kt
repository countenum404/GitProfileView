package com.shabashov.gitprofileview.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shabashov.gitprofileview.presentation.finder.SearchProfileView
import com.shabashov.gitprofileview.presentation.finder.SearchProfileViewModel
import com.shabashov.gitprofileview.presentation.history.HistoryView
import com.shabashov.gitprofileview.presentation.history.HistoryViewModel
import com.shabashov.gitprofileview.presentation.ui.theme.GitProfileViewTheme

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SearchScreen.route) {
        composable(Screen.SearchScreen.route) {
            val viewModel: SearchProfileViewModel = hiltViewModel()
            GitProfileViewTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                navController.navigate(Screen.ProfilesHistoryScreen.route)
                            },
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSurface,
                            shape = CircleShape
                        ) {
                            Icon(
                                imageVector = Icons.Default.Album,
                                contentDescription = "History",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                ) { innerPadding ->
                    SearchProfileView(modifier = Modifier.padding(innerPadding))
                }
            }
        }

        composable(Screen.ProfilesHistoryScreen.route) {
            val viewModel: HistoryViewModel = hiltViewModel()
            HistoryView(viewModel = viewModel)
        }
    }
}

sealed class Screen(val route: String) {
    data object SearchScreen: Screen(route = "search")
    data object ProfilesHistoryScreen: Screen(route = "history")
}

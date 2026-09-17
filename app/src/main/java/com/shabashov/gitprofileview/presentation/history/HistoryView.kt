package com.shabashov.gitprofileview.presentation.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.shabashov.gitprofileview.R
import com.shabashov.gitprofileview.presentation.ui.components.CenterText
import com.shabashov.gitprofileview.presentation.ui.components.ProfilesColumn
import com.shabashov.gitprofileview.presentation.ui.components.Title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryView(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Title(
                        modifier = Modifier,
                        text = stringResource(R.string.visited_profiles)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val currentState = state) {
                HistoryScreenState.ErrorState -> {
                    CenterText(
                        modifier = Modifier.weight(1f),
                        text = "Some error. Check logs"
                    )
                }
                HistoryScreenState.LoadingState -> {
                    CenterText(
                        modifier = Modifier.weight(1f),
                        text = "Loading...",
                    )
                }
                is HistoryScreenState.ViewedProfilesState -> {
                    val profiles = currentState.profiles
                    ProfilesColumn(
                        modifier = Modifier,
                        profiles = profiles,
                        onClick = { }
                    )
                }
            }
        }
    }

}
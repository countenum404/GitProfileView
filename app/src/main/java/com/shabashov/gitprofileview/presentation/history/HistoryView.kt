package com.shabashov.gitprofileview.presentation.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.shabashov.gitprofileview.presentation.ui.components.CenterText
import com.shabashov.gitprofileview.presentation.ui.components.ProfilesColumn
import com.shabashov.gitprofileview.presentation.ui.components.Title

@Composable
fun HistoryView(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Title(
            modifier = Modifier.padding(start = 8.dp),
            text = "Visited profiles"
        )

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
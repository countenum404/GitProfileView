package com.shabashov.gitprofileview.presentation.finder

import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.shabashov.gitprofileview.presentation.ui.components.CenterText
import com.shabashov.gitprofileview.presentation.ui.components.ProfilesColumn
import com.shabashov.gitprofileview.presentation.ui.components.SubTitle
import com.shabashov.gitprofileview.presentation.ui.components.Title
import com.shabashov.gitprofileview.presentation.ui.theme.JetBrainsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchProfileView(
    modifier: Modifier = Modifier,
    viewModel: SearchProfileViewModel = hiltViewModel(),
    onFloatingActionButtonClick: () -> Unit,
    onSettingsClicked: () -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.d("SearchProfileView", result.resultCode.toString())
    }

    val state by viewModel.state.collectAsState()
    val currentState = state

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Title(
                        modifier = Modifier,
                        text = "Search"
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                actions = {
                    IconButton(onClick = onSettingsClicked) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "settings",
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onFloatingActionButtonClick()
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
        Column(modifier = modifier.padding(innerPadding)) {


            SearchField(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                query = state.query,
                onValueChange = {
                    viewModel.processCommand(SearchProfileCommands.Query(it))
                    Log.d("SearchField", "Message: $it")
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            when (currentState) {
                SearchProfileScreenState.Initial -> {
                    CenterText(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .weight(1f),
                        text = "GitHub profiles searching tool"
                    )
                }
                is SearchProfileScreenState.Found -> {

                    SubTitle(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "Found accounts"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ProfilesColumn(
                        profiles = currentState.profiles,
                        onClick = { profile ->
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                "https://github.com/${profile.name}".toUri()
                            )
                            launcher.launch(intent)
                            val command = SearchProfileCommands.OpenRepoInBrowser(profile)
                            viewModel.processCommand(command)
                        }
                    )
                }
                is SearchProfileScreenState.Searching -> {
                    CenterText(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .weight(1f),
                        text = "Loading..."
                    )
                }
                is SearchProfileScreenState.NotFound -> {
                    CenterText(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .weight(1f),
                        text = "Not found any profile with name ${currentState.query}"
                    )
                }
            }
        }
    }


}



@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    query: String = "",
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        placeholder = {
            Text(
                "Type to search a profiles",
                fontFamily = JetBrainsFontFamily,
            )
        },
        singleLine = true,
        onValueChange = onValueChange,
        value = query,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search bar"
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Transparent,
        ),
    )
}


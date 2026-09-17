package com.shabashov.gitprofileview.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.shabashov.gitprofileview.R
import com.shabashov.gitprofileview.domain.entity.AppLanguage
import com.shabashov.gitprofileview.presentation.navigation.Screen
import com.shabashov.gitprofileview.presentation.ui.components.CenterText
import com.shabashov.gitprofileview.presentation.ui.components.SubTitle
import com.shabashov.gitprofileview.presentation.ui.components.Title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()


    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Title(
                        modifier = Modifier,
                        text = stringResource(R.string.settings)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (val currentState = state) {
                SettingsScreenState.Loading -> CenterText(
                    modifier = Modifier,
                    text = stringResource(R.string.loading)
                )
                is SettingsScreenState.SettingsLoaded -> {
                    val settings = currentState.settings

                    SettingsBottomSheet(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        settingName = stringResource(R.string.language),
                        settings = AppLanguage.entries.map { it.name }.toList(),
                        icon = Icons.Default.Language,
                        currentSelection = settings.language.name,
                        onItemClick = { language ->
                            viewModel.processCommand(
                                SettingsScreenCommands.ChangeLanguage(AppLanguage.valueOf(language))
                            )
                        },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBottomSheet(
    modifier: Modifier = Modifier,
    settingName: String,
    icon: ImageVector,
    settings: List<String>,
    currentSelection: String,
    onItemClick: (String) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.clickable {
            showBottomSheet = !showBottomSheet
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Icon"
        )
        SubTitle(
            text = settingName
        )
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            SubTitle(
                modifier = Modifier.padding(8.dp),
                text = stringResource(R.string.select, settingName.lowercase())
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {

                items(settings) { setting ->
                    val isSelected = setting == currentSelection
                    ListItem(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.background
                            )
                            .clickable {
                                onItemClick(setting)
                                showBottomSheet = false
                            },
                        headlineContent = {
                            Text(
                                modifier = Modifier.background(
                                    color = MaterialTheme.colorScheme.background
                                ),
                                text = setting
                            )
                        },
                        leadingContent = {
                            RadioButton(
                                modifier = Modifier.background(
                                    color = MaterialTheme.colorScheme.background
                                ),
                                selected = isSelected,
                                onClick = {
                                    onItemClick(setting)
                                },
                                colors = RadioButtonDefaults.colors().copy(
                                    selectedColor = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        },
                        colors = ListItemDefaults.colors().copy(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    )
                }
            }

        }
    }
}

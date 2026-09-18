package com.shabashov.gitprofileview.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shabashov.gitprofileview.domain.entity.AppLanguage
import com.shabashov.gitprofileview.domain.entity.Settings
import com.shabashov.gitprofileview.domain.entity.Theme
import com.shabashov.gitprofileview.domain.usecase.settings.ChangeLanguageUseCase
import com.shabashov.gitprofileview.domain.usecase.settings.ChangeThemeUseCase
import com.shabashov.gitprofileview.domain.usecase.settings.GetSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val changeLanguageUseCase: ChangeLanguageUseCase,
    private val changeThemeUseCase: ChangeThemeUseCase,
    private val getSettingsUseCase: GetSettingsUseCase
): ViewModel() {
    private val _state = MutableStateFlow<SettingsScreenState>(SettingsScreenState.Loading)
    val state: StateFlow<SettingsScreenState>
        get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getSettingsUseCase().collect { settings ->
                _state.update { SettingsScreenState.SettingsLoaded(settings = settings) }
            }
        }
    }

    fun processCommand(command: SettingsScreenCommands) {
        when (command) {
            is SettingsScreenCommands.ChangeLanguage -> viewModelScope.launch {
                changeLanguageUseCase(command.appLanguage)
            }

            is SettingsScreenCommands.ChangeTheme -> viewModelScope.launch {
                changeThemeUseCase(command.theme)
            }
        }
    }
}

sealed interface SettingsScreenState {
    data object Loading: SettingsScreenState
    data class SettingsLoaded(val settings: Settings): SettingsScreenState
}

sealed interface SettingsScreenCommands {
    data class ChangeLanguage(val appLanguage: AppLanguage): SettingsScreenCommands
    data class ChangeTheme(val theme: Theme): SettingsScreenCommands
}
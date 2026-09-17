package com.shabashov.gitprofileview.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shabashov.gitprofileview.domain.entity.AppLanguage
import com.shabashov.gitprofileview.domain.usecase.settings.ChangeLanguageUseCase
import com.shabashov.gitprofileview.domain.usecase.settings.GetLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val changeLanguageUseCase: ChangeLanguageUseCase,
    private val getLanguageUseCase: GetLanguageUseCase
): ViewModel() {
    private val _state = MutableStateFlow<SettingsScreenState>(SettingsScreenState.Loading)
    val state: StateFlow<SettingsScreenState>
        get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getLanguageUseCase().onEach { language ->
                val settings = Settings(
                    language = language
                )
                _state.update { SettingsScreenState.SettingsLoaded(settings) }
            }.collect()
        }
    }

    fun processCommand(command: SettingsScreenCommands) {
        when (command) {
            is SettingsScreenCommands.ChangeLanguage -> viewModelScope.launch {
                changeLanguageUseCase(command.appLanguage)
            }
        }
    }
}

data class Settings(
    val language: AppLanguage
)

sealed interface SettingsScreenState {
    data object Loading: SettingsScreenState
    data class SettingsLoaded(val settings: Settings): SettingsScreenState
}

sealed interface SettingsScreenCommands {
    data class ChangeLanguage(val appLanguage: AppLanguage): SettingsScreenCommands
}
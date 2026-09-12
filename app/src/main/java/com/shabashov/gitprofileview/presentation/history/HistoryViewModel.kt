package com.shabashov.gitprofileview.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shabashov.gitprofileview.domain.GetAllViewedProfilesUseCase
import com.shabashov.gitprofileview.domain.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getAllViewedProfilesUseCase: GetAllViewedProfilesUseCase
): ViewModel() {
    private val _state = MutableStateFlow<HistoryScreenState>(HistoryScreenState.LoadingState)
    val state: StateFlow<HistoryScreenState>
        get() = _state.asStateFlow()
    
    init {
        viewModelScope.launch {
            _state.update { HistoryScreenState.LoadingState }

            runCatching {
                getAllViewedProfilesUseCase()
            }.onFailure {
                _state.update { HistoryScreenState.ErrorState }
            }.onSuccess {
                it.collect { profiles ->
                    _state.update { HistoryScreenState.ViewedProfilesState(profiles) }
                }
            }
        }
    }
}

sealed interface HistoryScreenState {
    data class ViewedProfilesState(val profiles: List<Profile>) : HistoryScreenState
    data object LoadingState : HistoryScreenState
    data object ErrorState : HistoryScreenState
}

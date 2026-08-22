package com.shabashov.gitprofileview.presentation.finder

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shabashov.gitprofileview.domain.Profile
import com.shabashov.gitprofileview.domain.SearchProfileUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class SearchProfileViewModel : ViewModel() {
    private val searchProfileUseCase = SearchProfileUseCase()

    private val _state = MutableStateFlow<SearchProfileScreenState>(
        SearchProfileScreenState.Initial
    )
    val state = _state.asStateFlow()

    private val query = MutableStateFlow(_state.value.query)

    init {
        query.onEach { input ->
            if (input.isNotBlank() && input.isNotEmpty()) {
                _state.update { SearchProfileScreenState.Searching(query = input) }
            } else {
                _state.update { SearchProfileScreenState.Initial }
            }
        }.debounce{
            500.milliseconds
        }.flatMapLatest {
            searchProfileUseCase(it)
        }.onEach { profiles ->
            if (profiles.isEmpty()) {
                _state.update { SearchProfileScreenState.NotFound(_state.value.query) }
            } else {
                _state.update {
                    SearchProfileScreenState.Found(
                        query = _state.value.query,
                        profiles = profiles
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun processCommand(command: SearchProfileCommands) {
        viewModelScope.launch {
            when (command) {
                is SearchProfileCommands.Query -> { query.update { command.text.trim() } }
                SearchProfileCommands.OpenRepoInBrowser -> {
                    Log.d("SearchProfileViewModel", "Opened in browser")
                }
            }
        }
    }

}

sealed interface SearchProfileScreenState {
    data object Initial: SearchProfileScreenState {
        override val query: String
            get() = ""
    }

    data class Found(
        override val query: String,
        val profiles: List<Profile>
    ) : SearchProfileScreenState

    data class Searching(override val query: String) : SearchProfileScreenState

    data class NotFound(override val query: String) : SearchProfileScreenState

    val query: String
}

sealed interface SearchProfileCommands {
    data class Query(val text: String) : SearchProfileCommands
    data object OpenRepoInBrowser : SearchProfileCommands
}

data class SearchProfileState(
    val query: String = "",
    val profiles: List<Profile> = listOf()
)


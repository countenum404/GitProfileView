package com.shabashov.gitprofileview.presentation.finder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shabashov.gitprofileview.data.TestRepository
import com.shabashov.gitprofileview.domain.Profile
import com.shabashov.gitprofileview.domain.SearchProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchProfileViewModel : ViewModel() {
    private val repository = TestRepository
    private val searchProfileUseCase = SearchProfileUseCase()
    private val _state = MutableStateFlow<SearchProfileState>(
        SearchProfileState("")
    )
    val state = _state.asStateFlow()

    private val query = MutableStateFlow(_state.value.query)

    init {
        query.onEach { input ->
            _state.update { it.copy(query = input) }
        }.flatMapLatest {
            searchProfileUseCase(it)
        }.onEach { profiles ->
            _state.update { it.copy(profiles = profiles) }
        }.launchIn(viewModelScope)
    }

    fun processCommand(command: SearchProfileCommands) {
        viewModelScope.launch {
            when (command) {
                is SearchProfileCommands.Query -> { query.update { command.text.trim() } }
                }
        }
    }

}


sealed interface SearchProfileCommands {
    data class Query(val text: String) : SearchProfileCommands
}

data class SearchProfileState(
    val query: String = "",
    val profiles: List<Profile> = listOf()
)


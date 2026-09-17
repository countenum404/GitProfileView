package com.shabashov.gitprofileview.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shabashov.gitprofileview.domain.entity.Theme
import com.shabashov.gitprofileview.domain.interfaces.settings.SettingsRepository
import com.shabashov.gitprofileview.presentation.navigation.NavGraph
import com.shabashov.gitprofileview.presentation.ui.theme.GitProfileViewTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    @Inject lateinit var settingsRepository: SettingsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val theme by settingsRepository.themeFlow
                .collectAsStateWithLifecycle(initialValue = Theme.WHITE)

            val darkTheme = when(theme) {
                Theme.WHITE -> false
                Theme.DARK -> true
            }

            GitProfileViewTheme(
                darkTheme = darkTheme
            ) {
                NavGraph()
            }
        }
    }
}
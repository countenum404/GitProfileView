package com.shabashov.gitprofileview.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.shabashov.gitprofileview.presentation.finder.SearchProfileView
import com.shabashov.gitprofileview.presentation.ui.theme.GitProfileViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitProfileViewTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SearchProfileView(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
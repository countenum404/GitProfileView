package com.shabashov.gitprofileview.presentation.ui.theme


import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.shabashov.gitprofileview.R

object GitProfileViewIcons {
    val GitHub: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.github)


    val GitLab: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.gitlab)


    val GitVerse: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.gitverse)

}
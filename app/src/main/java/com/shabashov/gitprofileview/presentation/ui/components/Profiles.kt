package com.shabashov.gitprofileview.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shabashov.gitprofileview.domain.Profile
import com.shabashov.gitprofileview.presentation.ui.theme.JetBrainsFontFamily


@Composable
fun ProfilesColumn(
    modifier: Modifier = Modifier,
    profiles: List<Profile>,
    onClick: (profile: Profile) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        profiles.forEachIndexed { index, profile ->
            item(key = index) {
                ProfileCard(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                    profile = profile,
                    onClick = { onClick(profile) }
                )
            }
        }
    }
}

@Composable
fun ProfileCard(
    modifier: Modifier = Modifier,
    profile: Profile,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = MaterialTheme.colorScheme.surface,)
            .border(
                shape = RoundedCornerShape(8.dp),
                width = 1.dp,
                color = MaterialTheme.colorScheme.tertiary
            )
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(
                8.dp
            )
        ) {
            Text(
                text = profile.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = JetBrainsFontFamily,
            )
            Text(
                text = profile.fullName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = JetBrainsFontFamily,
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "followers"
                )
                Text(
                    text = "Followers: ${profile.followersNumber}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = JetBrainsFontFamily,
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = "following number"
                )
                Text(
                    text = "Following: ${profile.followingNumber}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = JetBrainsFontFamily,
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "repositories"
                )
                Text(
                    text = "Repositories: ${profile.publicRepositories}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = JetBrainsFontFamily,
                )
            }

        }
    }
}
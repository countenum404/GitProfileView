package com.shabashov.gitprofileview.presentation.finder

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shabashov.gitprofileview.domain.Profile
import com.shabashov.gitprofileview.presentation.ui.theme.JetBrainsFontFamily

@Preview
@Composable
fun SearchProfileView(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: SearchProfileViewModel = viewModel()
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.d("SearchProfileView", result.resultCode.toString())
    }

    val state by viewModel.state.collectAsState()

    Column(modifier = modifier) {
        SearchField(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp),
            query = state.query,
            onValueChange = {
                viewModel.processCommand(SearchProfileCommands.Query(it))
                Log.d("SearchField", "Message: $it")
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        SubTitle(
            modifier = Modifier.padding(start = 8.dp),
            text = "Found accounts"
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            state.profiles.forEachIndexed { index, profile ->
                item(key = index) {
                    ProfileCard(
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                        profile = profile,
                        onClick = {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://github.com/${profile.name}")
                            )
                            launcher.launch(intent)
                            viewModel.processCommand(SearchProfileCommands.OpenRepoInBrowser)
                        }
                    )
                }
            }
        }
    }


}

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    query: String = "",
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        placeholder = {
            Text(
                "Type to search a profiles",
                fontFamily = JetBrainsFontFamily,
            )
        },
        singleLine = true,
        onValueChange = onValueChange,
        value = query,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search bar"
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
    )
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
            .border(
                shape = RoundedCornerShape(8.dp),
                width = 0.dp,
                color = MaterialTheme.colorScheme.tertiary
            )
            .background(color = MaterialTheme.colorScheme.surface)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(
                8.dp
            )
        ) {
            Text(
                text = "${profile.name}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = JetBrainsFontFamily,
            )
            Text(
                text = "${profile.fullName}",
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

@Composable
private fun SubTitle(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontWeight = FontWeight.Bold,
        fontFamily = JetBrainsFontFamily,
        fontSize = 14.sp
    )
}

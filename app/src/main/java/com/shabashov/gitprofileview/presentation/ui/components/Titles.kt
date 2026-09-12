package com.shabashov.gitprofileview.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.shabashov.gitprofileview.presentation.ui.theme.JetBrainsFontFamily

@Composable
fun SubTitle(
    modifier: Modifier = Modifier,
    text: String = ""
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

@Composable
fun CenterText(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontFamily = JetBrainsFontFamily,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview(showSystemUi =  true)
fun Title(
    modifier: Modifier = Modifier,
    text: String = "test"
) {
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colorScheme.onSurface,
        fontFamily = JetBrainsFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    )
}
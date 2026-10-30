package com.example.sonora.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sonora.R
import com.example.sonora.ui.theme.SonoraTheme

@Composable
fun EmptyState(
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.empty_state_title),
    message: String = stringResource(R.string.empty_state_message),
    actionLabel: String = stringResource(R.string.empty_state_action),
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Outlined.MusicNote,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(72.dp),
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(24.dp))
        Button(onClick = onAction) {
            Text(actionLabel)
        }
    }
}

@Preview(name = "Empty — светлая")
@Composable
private fun EmptyStateLightPreview() {
    SonoraTheme(darkTheme = false) { Surface { EmptyState(onAction = {}) } }
}

@Preview(name = "Empty — тёмная", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EmptyStateDarkPreview() {
    SonoraTheme(darkTheme = true) { Surface { EmptyState(onAction = {}) } }
}
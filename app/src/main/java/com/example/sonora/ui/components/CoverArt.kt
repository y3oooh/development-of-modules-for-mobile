package com.example.sonora.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Album
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sonora.R
import com.example.sonora.ui.theme.SonoraTheme

@Composable
fun CoverArt(
    coverRes: Int?,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(20.dp)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center,
    ) {
        if (coverRes != null) {
            androidx.compose.foundation.Image(
                painter = painterResource(coverRes),
                contentDescription = stringResource(R.string.cover_art_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().aspectRatio(1f).clip(shape),
            )
        } else {
            Icon(
                imageVector = Icons.Outlined.Album,
                contentDescription = stringResource(R.string.cover_art_description),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth(0.3f),
            )
        }
    }
}

@Preview(name = "CoverArt — светлая", showBackground = false)
@Composable
private fun CoverArtLightPreview() {
    SonoraTheme(darkTheme = false) { Surface { CoverArt(coverRes = null, modifier = Modifier) } }
}

@Preview(name = "CoverArt — тёмная", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CoverArtDarkPreview() {
    SonoraTheme(darkTheme = true) { Surface { CoverArt(coverRes = null, modifier = Modifier) } }
}
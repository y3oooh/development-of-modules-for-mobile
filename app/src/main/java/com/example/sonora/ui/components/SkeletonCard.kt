package com.example.sonora.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sonora.ui.theme.SonoraTheme

@Composable
fun SkeletonCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .shimmer(MaterialTheme.shapes.extraLarge)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(24.dp)
                .shimmer(RoundedCornerShape(6.dp))
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(16.dp)
                .shimmer(RoundedCornerShape(6.dp))
        )
        Spacer(Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .shimmer(RoundedCornerShape(3.dp))
        )
    }
}

@Preview(name = "Skeleton — светлая")
@Composable
private fun SkeletonCardLightPreview() {
    SonoraTheme(darkTheme = false) {
        Surface { SkeletonCard() }
    }
}

@Preview(name = "Skeleton — тёмная", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SkeletonCardDarkPreview() {
    SonoraTheme(darkTheme = true) {
        Surface { SkeletonCard() }
    }
}
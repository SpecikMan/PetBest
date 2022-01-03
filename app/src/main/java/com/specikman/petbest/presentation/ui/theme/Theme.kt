package com.specikman.petbest.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import ark.coding.grocery.ui.theme.Typography

private val LightColorPalette = lightColors(
    primary = Orange,
    primaryVariant = Orange,
    secondary = Orange
)

@Composable
fun HomeTheme(content: @Composable() () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
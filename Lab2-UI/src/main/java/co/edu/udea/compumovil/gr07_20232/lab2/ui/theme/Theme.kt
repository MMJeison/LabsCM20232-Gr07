package co.edu.udea.compumovil.gr07_20232.lab2.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun JetcasterTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = JetcasterColors,
        typography = JetcasterTypography,
        shapes = JetcasterShapes,
        content = content
    )
}
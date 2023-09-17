package co.edu.udea.compumovil.gr07_20232.lab1.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CText(text: String, modifier: Modifier = Modifier ) {
    Text(
        text = text,
        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
        modifier = modifier.padding(start = 8.dp, end = 8.dp),
        color = Color.Black
    )
}
package co.edu.udea.compumovil.gr07_20232.lab1.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CRadioButton(
    text: String,
    selectedOption: MutableState<Int>,
    options: List<String>,
    modifier: Modifier = Modifier,
    icon: ImageVector
) {
    val radioOptions = remember(options) { mutableStateListOf(*options.toTypedArray()) }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CIcon(icon = icon)
        CText(
            text = text
        )
        radioOptions.forEachIndexed { index, option ->
            RadioButton(
                selected = index == selectedOption.value,
                onClick = { selectedOption.value = index },
                modifier = Modifier
                    .clickable { selectedOption.value = index }
                    .padding(end = 4.dp),
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color.Red, // Cambia el color del radio seleccionado
                    unselectedColor = Color.Gray // Cambia el color del radio no seleccionado
                )
            )
            CText(
                text = option,
                modifier = Modifier.clickable { selectedOption.value = index }
            )
        }
    }
}
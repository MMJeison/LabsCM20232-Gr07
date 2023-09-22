package co.edu.udea.compumovil.gr07_20232.lab1.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun CTextField(
    value: MutableState<TextFieldValue>,
    modifier: Modifier,
    placeholderText: String,
    icon: ImageVector,
    onKeyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text
    )
) {
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CIcon(
            icon = icon,
        )
        TextField(
            value = value.value,
            onValueChange = { value.value = it },
            placeholder = { Text(text = placeholderText) },
            keyboardOptions = onKeyboardOptions,
            singleLine = true,
            modifier = Modifier
                .padding(16.dp)
                .border(
                    width = 1.8.dp,
                    color = Color(76, 175, 80),
                    shape = RoundedCornerShape(15.dp)
                )
                .onFocusChanged { },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black
            )
        )
    }
}
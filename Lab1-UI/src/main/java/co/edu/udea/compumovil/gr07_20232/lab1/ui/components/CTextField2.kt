package co.edu.udea.compumovil.gr07_20232.lab1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CTextField2(
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    emailType: Int = 0
){
    val inputValue = remember { mutableStateOf(TextFieldValue()) }

    TextField(
        value = inputValue.value,
        onValueChange = { inputValue.value = it },
        placeholder = { Text(text = placeholder) },
        modifier = Modifier.fillMaxWidth()
            .padding(30.dp).then(modifier)
            .background(Color.White)
            .border(
                width = 1.8.dp,
                color = Color(76, 175, 80),
                shape = RoundedCornerShape(15.dp)
            ),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.sp
        ),
        trailingIcon =
        {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Rounded.KeyboardArrowDown,
                contentDescription = "arrow",
                tint = Color.Black
            )
        },
        keyboardOptions = keyboardOptions
    )

    if(emailType == 1){
        var text by remember { mutableStateOf("") }
        var isEmailValid by remember { mutableStateOf(true) }

        if (!isEmailValid) {
            Text(
                text = "Ingrese una dirección de correo electrónico válida",
                color = Color.Red,
                fontSize = 12.sp
            )
        }
    }
}

fun isValidEmail(email: String): Boolean {
    val regexPattern = Regex("^\\S+@\\S+\\.\\S+\$")
    return regexPattern.matches(email)
}
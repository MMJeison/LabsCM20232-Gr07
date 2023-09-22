package co.edu.udea.compumovil.gr07_20232.lab1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CButton(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    /*Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){*/
        Button(
            onClick = onClick,
            modifier = modifier.then(Modifier.padding(16.dp)
                .background(
                    color = Color(76, 175, 80)
                )),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(76, 175, 80)
            ),
            shape = CutCornerShape(25)
        ) {
            CText(text = label,
                modifier = Modifier
                    .background(Color.Transparent)
            )
        }
    /*Button(
        onClick = {
        },
        modifier = Modifier
            //.align(Alignment.CenterHorizontally)
            .padding(top = 16.dp) // Espaciado entre el texto y el botón
    ) {
        Text("Botón")
    }*/
    //}

}

@Preview
@Composable
fun ButtonPreview() {
    CButton(label = "Label") {
        
    }
}
package co.edu.udea.compumovil.gr07_20232.lab1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
        modifier = modifier.then(
            Modifier.background(color = Color.Transparent)
        ),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){*/
        Button(
            onClick = onClick,
            modifier = Modifier.padding(16.dp)
                .background(
                    color = Color(76, 175, 80)
                ),
            colors = ButtonDefaults.buttonColors(
                //containerColor = Color(76, 175, 80)
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(5.dp)
        ) {
            CText(text = label,
                modifier = Modifier
                    .background(Color.Transparent)
            )
        }
    //}

}

@Preview(showBackground = true)
@Composable
fun ButtonPreview() {
    Button(onClick = { /*TODO*/ }) {
        
    }
}
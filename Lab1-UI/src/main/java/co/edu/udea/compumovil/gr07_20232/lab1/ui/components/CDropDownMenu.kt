package co.edu.udea.compumovil.gr07_20232.lab1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CDropDownMenu(
    selectedValue: MutableState<String>,
    options: List<String>,
    label: String,
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier
    ) {
        var expanded by remember { mutableStateOf(false) }
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            CIcon(icon = Icons.Default.Info)
            CText(text = label)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                    .clickable { expanded = true },
                verticalAlignment = Alignment.CenterVertically
            ) {
                CText(text = selectedValue.value, modifier = Modifier.padding(vertical = 5.dp))
                CIcon(
                    icon = Icons.Default.ArrowDropDown
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            CText(text = option)
                        },
                        onClick = {
                            selectedValue.value = option
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
package co.edu.udea.compumovil.gr07_20232.lab1.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar
import java.util.Date

@Composable
fun CDateRangePicker(
    modifier: Modifier = Modifier,
    value: MutableState<String>
) {
    Column (
        modifier = modifier
    ){
        val context = LocalContext.current;
        val day:Int;
        val month:Int;
        val year:Int;
        val calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        calendar.time = Date()

        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, day: Int ->
                value.value = "${day}/${month + 1}/${year}"
            }, year, month, day
        )

        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ){
            CIcon(icon = Icons.Default.DateRange);
            CText(text = "Fecha de cumpleaños");
        }
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            CText(text = "${value.value}");
        }
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ){
            Button(
                onClick = {
                datePickerDialog.show()
            }, colors = ButtonDefaults.buttonColors(
                MaterialTheme.colorScheme.secondary
            ) ) {
                CText(text = "Seleccionar fecha")
            }
        }
    }
}
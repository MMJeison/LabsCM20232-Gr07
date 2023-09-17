package co.edu.udea.compumovil.gr07_20232.lab1.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import co.edu.udea.compumovil.gr07_20232.lab1.ui.components.CDateRangePicker
import co.edu.udea.compumovil.gr07_20232.lab1.ui.components.CDropDownMenu
import co.edu.udea.compumovil.gr07_20232.lab1.ui.components.CRadioButton
import co.edu.udea.compumovil.gr07_20232.lab1.ui.components.CTextField
import co.edu.udea.compumovil.gr07_20232.lab1.ui.components.CTitle


@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalComposeUiApi
@Composable
fun PersonalDataActivity() {
    ConstraintLayout (
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        val name = remember { mutableStateOf(TextFieldValue()) }
        val lastName = remember { mutableStateOf(TextFieldValue()) }
        val sex = remember { mutableIntStateOf(-1) }
        val dateOfBird = remember { mutableStateOf("") }
        val schoolGrade = remember { mutableStateOf("Seleccione su grado de escolaridad")  }
        val schoolGradeOptios = listOf("Primaria", "Bachiller", "Tecnico", "Tecnólogo", "Profesional")
        val sexOptions = listOf("Hombre", "Mujer")


        val (titleRef, nameRef, lastNameRef, sexRef, dateOfBirdRef, schoolGradeRef ) = createRefs()
        CTitle(
            title = "Información personal",
            modifier = Modifier.constrainAs(titleRef) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        )
        CTextField(
            value = name,
            placeholderText = "Nombre",
            icon = Icons.Default.Person,
            modifier = Modifier.constrainAs(nameRef) {
                top.linkTo(titleRef.bottom, margin = 16.dp)
            }
        )
        CTextField(
            value = lastName,
            placeholderText = "Apellido",
            icon = Icons.Default.AccountCircle,
            modifier = Modifier.constrainAs(lastNameRef) {
                top.linkTo(nameRef.bottom, margin = 16.dp)
            }
        )
        CRadioButton(
            text = "Sexo",
            selectedOption = sex,
            options = sexOptions,
            modifier = Modifier.constrainAs(sexRef) {
                top.linkTo(lastNameRef.bottom, margin = 16.dp)
            },
            icon = Icons.Default.Face
        )
        CDateRangePicker(
            value = dateOfBird,
            modifier = Modifier.constrainAs(dateOfBirdRef) {
                top.linkTo(sexRef.bottom, margin = 16.dp)
            }
        )
        CDropDownMenu(
            selectedValue = schoolGrade,
            options = schoolGradeOptios,
            label = "Grado de escolaridad",
            modifier = Modifier.constrainAs(schoolGradeRef) {
                top.linkTo(dateOfBirdRef.bottom, margin = 16.dp)
            }
        )
    }
}

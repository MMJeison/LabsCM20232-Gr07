package co.edu.udea.compumovil.gr07_20232.lab1.ui

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import co.edu.udea.compumovil.gr07_20232.lab1.R
import co.edu.udea.compumovil.gr07_20232.lab1.ui.components.CButton
import co.edu.udea.compumovil.gr07_20232.lab1.ui.components.CDateRangePicker
import co.edu.udea.compumovil.gr07_20232.lab1.ui.components.CDropDownMenu
import co.edu.udea.compumovil.gr07_20232.lab1.ui.components.CRadioButton
import co.edu.udea.compumovil.gr07_20232.lab1.ui.components.CTextField
import co.edu.udea.compumovil.gr07_20232.lab1.ui.components.CTitle


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PersonalDataScreen(
    navController: NavHostController
){
    //val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val schoolGradeOptions = listOf(
        stringResource(R.string.primary_school_text),
        stringResource(R.string.high_school_text),
        stringResource(R.string.vocational_text),
        stringResource(R.string.undergraduate_text)
    )
    val sexOptions = listOf(
        stringResource(R.string.male_text),
        stringResource(R.string.female_text)
    )

    when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            PersonalDataActivity(
                navController = navController,
                schoolGradeOptions = schoolGradeOptions,
                sexOptions = sexOptions
            )
        }
        else -> {
            PersonalDataActivityLandscape(
                navController = navController,
                schoolGradeOptions = schoolGradeOptions,
                sexOptions = sexOptions
            )
        }
    }
}

val name = mutableStateOf(TextFieldValue(""))
val lastName = mutableStateOf(TextFieldValue(""))
val sex = mutableIntStateOf(-1)
val dateOfBird  =  mutableStateOf("")
//var schoolGradePlaceholder = stringResource(R.string.select_scholar_grade_text)
val schoolGrade = mutableStateOf("")




@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalComposeUiApi
@Composable
fun PersonalDataActivity(
    navController: NavHostController,
    schoolGradeOptions: List<String>,
    sexOptions: List<String>
    ) {
    ConstraintLayout (
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        val context = LocalContext.current
        val (titleRef, nameRef, lastNameRef, sexRef, dateOfBirdRef, schoolGradeRef, nextButton ) = createRefs()
        CTitle(
            title = stringResource(R.string.personal_data_title),
            modifier = Modifier.constrainAs(titleRef) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        )
        CTextField(
            value = name,
            placeholderText = stringResource(R.string.name_label),
            icon = Icons.Default.Person,
            modifier = Modifier.constrainAs(nameRef) {
                top.linkTo(titleRef.bottom, margin = 16.dp)
            },
            onKeyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        CTextField(
            value = lastName,
            placeholderText = stringResource(R.string.surname_label),
            icon = Icons.Default.AccountCircle,
            modifier = Modifier.constrainAs(lastNameRef) {
                top.linkTo(nameRef.bottom, margin = 16.dp)
            }
        )
        CRadioButton(
            text = stringResource(R.string.sex_label),
            selectedOption = sex,
            options = sexOptions,
            modifier = Modifier.constrainAs(sexRef) {
                top.linkTo(lastNameRef.bottom, margin = 16.dp)
            },
            icon = Icons.Default.Face
        )
        CDateRangePicker(
            label = stringResource(R.string.birth_date_label),
            value = dateOfBird,
            modifier = Modifier.constrainAs(dateOfBirdRef) {
                top.linkTo(sexRef.bottom, margin = 16.dp)
            }
        )
        CDropDownMenu(
            selectedValue = schoolGrade,
            options = schoolGradeOptions,
            label = stringResource(R.string.scholar_grade_label),
            modifier = Modifier.constrainAs(schoolGradeRef) {
                top.linkTo(dateOfBirdRef.bottom, margin = 16.dp)
            }
        )
        CButton(
            label = stringResource(R.string.next_text),
            modifier = Modifier.constrainAs(nextButton){
                top.linkTo(schoolGradeRef.bottom, margin = 8.dp)
            },
            onClick = {
                if(areFieldsValid(context)) {
                    navController.navigate("ContactData")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataActivityLandscape(
    navController: NavHostController,
    schoolGradeOptions: List<String>,
    sexOptions: List<String>
) {
    ConstraintLayout (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        val context = LocalContext.current
        val (titleRef, nameRef, lastNameRef, sexRef, dateOfBirdRef, schoolGradeRef, nextButton ) = createRefs()
        CTitle(
            title = stringResource(R.string.personal_data_title),
            modifier = Modifier.constrainAs(titleRef) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        )
        CTextField(
            value = name,
            placeholderText = stringResource(R.string.name_label),
            icon = Icons.Default.Person,
            modifier = Modifier.constrainAs(nameRef) {
                top.linkTo(titleRef.bottom, margin = 16.dp)
            }
        )
        CTextField(
            value = lastName,
            placeholderText = stringResource(R.string.surname_label),
            icon = Icons.Default.AccountCircle,
            modifier = Modifier.constrainAs(lastNameRef) {
                top.linkTo(titleRef.bottom, margin = 16.dp)
                absoluteLeft.linkTo(nameRef.absoluteRight, margin = 16.dp)
            },
            onKeyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        CRadioButton(
            text = stringResource(R.string.sex_label),
            selectedOption = sex,
            options = sexOptions,
            modifier = Modifier.constrainAs(sexRef) {
                top.linkTo(nameRef.bottom, margin = 16.dp)
            },
            icon = Icons.Default.Face
        )
        CDateRangePicker(
            label = stringResource(R.string.birth_date_label),
            value = dateOfBird,
            modifier = Modifier.constrainAs(dateOfBirdRef) {
                top.linkTo(sexRef.bottom, margin = 16.dp)
            }
        )
        CDropDownMenu(
            selectedValue = schoolGrade,
            options = schoolGradeOptions,
            label = stringResource(R.string.scholar_grade_label),
            modifier = Modifier.constrainAs(schoolGradeRef) {
                absoluteLeft.linkTo(dateOfBirdRef.absoluteRight, margin = 16.dp)
            }
        )
        CButton(
            label = stringResource(R.string.next_text),
            modifier = Modifier.constrainAs(nextButton){
                top.linkTo(schoolGradeRef.bottom, margin = 8.dp)
                absoluteRight.linkTo(parent.absoluteRight, margin = 16.dp)
            },
            onClick = {
                if (areFieldsValid(context = context)) {
                    navController.navigate("ContactData")
                }
            }
        )
    }
}

fun areFieldsValid(context: Context):Boolean {
    if(name.value.text.isBlank()) {
        Toast.makeText(context, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
        return false
    }
    if(lastName.value.text.isBlank()) {
        Toast.makeText(context, "El apellido no puede estar vacío", Toast.LENGTH_SHORT).show()
        return false
    }
    if(sex.value == -1) {
        Toast.makeText(context, "Debe seleccionar su sexo", Toast.LENGTH_SHORT).show()
        return false
    }
    if(dateOfBird.value.isBlank()) {
        Toast.makeText(context, "Debe seleccionar una fecha de nacimiento", Toast.LENGTH_SHORT).show()
        return false
    }
    if(schoolGrade.value.isBlank()) {
        Toast.makeText(context, "Debe seleccionar un grado de escolarida", Toast.LENGTH_SHORT).show()
        return false
    }
    return true
}


@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PersonalDataPreview() {
    //PersonalDataActivity(navController)
}
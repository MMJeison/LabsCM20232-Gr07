package co.edu.udea.compumovil.gr07_20232.lab1.ui

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
fun PersonalDataScreen(){
    //val context = LocalContext.current
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            PersonalDataActivity()
        }
        else -> {
            PersonalDataActivityLandscape()
        }
    }
}
var name by  mutableStateOf("")
var lastName by mutableStateOf("")
var sex  by mutableIntStateOf(-1)
var dateOfBird  by  mutableStateOf("")
//var schoolGradePlaceholder = stringResource(R.string.select_scholar_grade_text)
var schoolGrade by mutableStateOf("")
val schoolGradeOptios = listOf(
    stringResource(R.string.primary_school_text),
    stringResource(R.string.high_school_text),
    stringResource(R.string.vocational_text),
    stringResource(R.string.undergraduate_text)
)

val sexOptions = listOf(
    stringResource(R.string.male_text),
    stringResource(R.string.female_text)
)
@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalComposeUiApi
@Composable
fun PersonalDataActivity(navController: NavHostController) {
    ConstraintLayout (
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
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
            options = schoolGradeOptios,
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
                navController.navigate("ContactData")
            }
        )
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PersonalDataPreview() {
    //PersonalDataActivity(navController)
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataActivityLandscape() {
    ConstraintLayout (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        val (titleRef, nameRef, lastNameRef, sexRef, dateOfBirdRef, schoolGradeRef ) = createRefs()
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
            options = schoolGradeOptios,
            label = stringResource(R.string.scholar_grade_label),
            modifier = Modifier.constrainAs(schoolGradeRef) {
                top.linkTo(dateOfBirdRef.bottom, margin = 16.dp)
            }
        )

    }
}

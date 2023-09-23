package co.edu.udea.compumovil.gr07_20232.lab1.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import co.edu.udea.compumovil.gr07_20232.lab1.R
import co.edu.udea.compumovil.gr07_20232.lab1.ui.components.CAutoComplete
import co.edu.udea.compumovil.gr07_20232.lab1.ui.components.CButton
import co.edu.udea.compumovil.gr07_20232.lab1.ui.components.CTextField
import co.edu.udea.compumovil.gr07_20232.lab1.ui.components.CTitle

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ContactDataScreen(
    navController: NavHostController
){
    //val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val countriesList = listOf(
        "Argentina",
        "Bolivia",
        "Brasil",
        "Chile",
        "Colombia",
        "Costa Rica",
        "Cuba",
        "Ecuador",
        "El Salvador",
        "Guatemala",
        "Haití",
        "Honduras",
        "México",
        "Nicaragua",
        "Panamá",
        "Paraguay",
        "Perú",
        "República Dominicana",
        "Uruguay",
        "Venezuela"
    )
    val citiesList = listOf(
        "Bogotá",
        "Medellín",
        "Cali",
        "Barranquilla",
        "Cartagena",
        "Bucaramanga",
        "Pereira",
        "Santa Marta",
        "Manizales",
        "Ibagué",
        "Villavicencio",
        "Cúcuta",
        "Neiva",
        "Pasto",
        "Tunja",
        "Popayán",
        "Sincelejo",
        "Montería",
        "Valledupar"
    )

    when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            ContactDataActivity(
                navController = navController,
                countriesList = countriesList,
                citiesList = citiesList
            )
        }
        else -> {
            ContactDataActivityLandscape(
                navController = navController,
                countriesList = countriesList,
                citiesList = citiesList
            )
        }
    }
}

val phoneNumber = mutableStateOf(TextFieldValue(""))
val addressInput = mutableStateOf(TextFieldValue(""))
val emailInput = mutableStateOf(TextFieldValue(""))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDataActivity(
    navController: NavHostController,
    countriesList: List<String>,
    citiesList: List<String>
) {
    ConstraintLayout (
        modifier = Modifier.padding(horizontal = 18.dp)
    ) {

        val (title, telephone, address, email, countries, cities, backButton, finishButton) = createRefs()

        CTitle(
            title = stringResource(R.string.contact_data_title),
            modifier = Modifier.constrainAs(title){
                top.linkTo(parent.top, margin = 16.dp)
            }
        )

        CTextField(
            placeholderText = stringResource(R.string.phone_number_label),
            modifier = Modifier.constrainAs(telephone){
                top.linkTo(title.bottom, margin = 18.dp)
            },
            icon = Icons.Default.Call,
            value = phoneNumber,
            onKeyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            )
        )

        CTextField(
            value = emailInput,
            placeholderText = stringResource(R.string.email_label),
            modifier = Modifier.constrainAs(email){
                top.linkTo(telephone.bottom, margin = 16.dp)
            },
            icon = Icons.Default.Email,
            onKeyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        CTextField(
            value = addressInput,
            placeholderText = stringResource(R.string.address_label),
            modifier = Modifier.constrainAs(address){
                top.linkTo(email.bottom, margin = 16.dp)
            },
            icon = Icons.Default.ArrowForward,
            onKeyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                autoCorrect = false,
                imeAction = ImeAction.Next
            )
        )

        CAutoComplete(
            title = stringResource(R.string.country_label),
            itemList = countriesList,
            modifier = Modifier.constrainAs(countries){
                top.linkTo(address.bottom, margin = 8.dp)
            },
            //icon = Icons.Default.LocationOn
        )

        CAutoComplete(
            title = stringResource(R.string.city_label),
            itemList = citiesList,
            modifier = Modifier.constrainAs(cities){
                top.linkTo(countries.bottom, margin = 8.dp)
            },
            //icon = Icons.Outlined.LocationOn
        )
        CButton(
            label = stringResource(R.string.back_text),
            modifier = Modifier.constrainAs(backButton){
                top.linkTo(cities.bottom, margin = 8.dp)
            },
            onClick = {
                navController.navigate("PersonalData")
            }
        )

        CButton(
            label = stringResource(R.string.finish_text),
            modifier = Modifier.constrainAs(finishButton){
                top.linkTo(cities.bottom, margin = 8.dp)
                absoluteLeft.linkTo(backButton.absoluteRight, margin = 8.dp)
            },
            onClick = { }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDataActivityLandscape(
    navController: NavHostController,
    countriesList: List<String>,
    citiesList: List<String>) {
    ConstraintLayout (
        modifier = Modifier.padding(horizontal = 18.dp)
    ) {

        val (title, telephone, address, email, countries, cities, backButton, finishButton) = createRefs()

        CTitle(
            title = stringResource(R.string.contact_data_title),
            modifier = Modifier.constrainAs(title){
                top.linkTo(parent.top, margin = 16.dp)
            }
        )

        CTextField(
            placeholderText = stringResource(R.string.phone_number_label),
            modifier = Modifier.constrainAs(telephone){
                top.linkTo(title.bottom, margin = 18.dp)
            },
            icon = Icons.Default.Call,
            value = phoneNumber,
            onKeyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            )
        )

        CTextField(
            value = emailInput,
            placeholderText = stringResource(R.string.email_label),
            modifier = Modifier.constrainAs(email){
                top.linkTo(title.bottom, margin = 18.dp)
                absoluteLeft.linkTo(telephone.absoluteRight, margin = 16.dp)
            },
            icon = Icons.Default.Email,
            onKeyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        CTextField(
            value = addressInput,
            placeholderText = stringResource(R.string.address_label),
            modifier = Modifier.constrainAs(address){
                top.linkTo(telephone.bottom, margin = 16.dp)
            }
                .fillMaxWidth(0.9f),
            icon = Icons.Default.ArrowForward,
            onKeyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                autoCorrect = false,
                imeAction = ImeAction.Next
            )
        )

        CAutoComplete(
            title = stringResource(R.string.country_label),
            itemList = countriesList,
            modifier = Modifier.constrainAs(countries){
                top.linkTo(address.bottom, margin = 8.dp)
            },
            //icon = Icons.Default.LocationOn
        )

        CAutoComplete(
            title = stringResource(R.string.city_label),
            itemList = citiesList,
            modifier = Modifier.constrainAs(cities){
                top.linkTo(address.bottom, margin = 8.dp)
                absoluteLeft.linkTo(countries.absoluteRight, margin = 8.dp)
            },
            //icon = Icons.Outlined.LocationOn
        )
        CButton(
            label = stringResource(R.string.back_text),
            modifier = Modifier.constrainAs(backButton){
                top.linkTo(countries.bottom, margin = 8.dp)
            },
            onClick = {
                navController.navigate("PersonalData")
            }
        )

        CButton(
            label = stringResource(R.string.finish_text),
            modifier = Modifier.constrainAs(finishButton){
                top.linkTo(cities.bottom, margin = 8.dp)
                absoluteLeft.linkTo(backButton.absoluteRight, margin = 8.dp)
            },
            onClick = { }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContactDataPreview() {
    //ContactDataActivity(navController)
}
package co.edu.udea.compumovil.gr07_20232.lab1.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import co.edu.udea.compumovil.gr07_20232.lab1.R
import co.edu.udea.compumovil.gr07_20232.lab1.ui.components.CAutoComplete
import co.edu.udea.compumovil.gr07_20232.lab1.ui.components.CTextField
import co.edu.udea.compumovil.gr07_20232.lab1.ui.components.CTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDataActivity() {
    ConstraintLayout (
        modifier = Modifier.padding(horizontal = 18.dp)
    ) {
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
        val (title, telephone, address, email, countries, cities) = createRefs()

        CTitle(
            title = stringResource(R.string.contact_data_title),
            modifier = Modifier.constrainAs(title){
                top.linkTo(parent.top, margin = 16.dp)
            }
        )
        val phoneNumber = remember { mutableStateOf(TextFieldValue()) }
        CTextField(
            placeholderText = stringResource(R.string.phone_number_label),
            modifier = Modifier.constrainAs(telephone){
                top.linkTo(title.bottom, margin = 18.dp)
            },
            icon = Icons.Default.Call,
            value = phoneNumber,
            onKeyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            )
        )
        val addressInput = remember { mutableStateOf(TextFieldValue()) }
        CTextField(
            value = addressInput,
            placeholderText = stringResource(R.string.address_label),
            modifier = Modifier.constrainAs(address){
                top.linkTo(telephone.bottom, margin = 16.dp)
            },
            icon = Icons.Default.ArrowForward,
            onKeyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                autoCorrect = false
            )
        )
        val emailInput = remember { mutableStateOf(TextFieldValue()) }
        CTextField(
            value = emailInput,
            placeholderText = stringResource(R.string.email_label),
            modifier = Modifier.constrainAs(email){
                top.linkTo(address.bottom, margin = 16.dp)
            },
            icon = Icons.Default.Email,
            onKeyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )

        CAutoComplete(
            title = stringResource(R.string.country_label),
            itemList = countriesList,
            modifier = Modifier.constrainAs(countries){
                top.linkTo(email.bottom, margin = 12.dp)
            }
        )

        CAutoComplete(
            title = stringResource(R.string.city_label),
            itemList = citiesList,
            modifier = Modifier.constrainAs(cities){
                top.linkTo(countries.bottom, margin = 8.dp)
            }
        )
    }
}
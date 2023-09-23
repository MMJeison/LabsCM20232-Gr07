package co.edu.udea.compumovil.gr07_20232.lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.edu.udea.compumovil.gr07_20232.lab1.ui.ContactDataScreen
import co.edu.udea.compumovil.gr07_20232.lab1.ui.PersonalDataScreen
import co.edu.udea.compumovil.gr07_20232.lab1.ui.components.rememberImeState
import co.edu.udea.compumovil.gr07_20232.lab1.ui.theme.LabsCM20232Gr07Theme

enum class ScreenData(){
    PersonalData,
    ContactData
}

@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LabsCM20232Gr07Theme {
                /*val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "PersonalData"){
                    composable("PersonalData"){
                        PersonalDataActivity(navController)
                    }
                    composable("ContactData"){
                        ContactDataActivity(navController)
                    }
                }*/



                val imeState = rememberImeState()
                val scrollState = rememberScrollState()

                LaunchedEffect(key1 = imeState.value){
                    if(imeState.value){
                        scrollState.animateScrollTo(scrollState.value + 250, tween(300))
                    }
                }


                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                    ,
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main()
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun Main() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "PersonalData"){
        composable("PersonalData"){
            PersonalDataScreen(navController)
        }
        composable("ContactData"){
            ContactDataScreen(navController)
        }
    }
    // Para probar las dos vistas commenta una y dejar la otra descomentada
    // Si se dejan las dos descomentadas se interponen :(
    //PersonalDataActivity(navController) // Vista 1
    // ContactDataActivity() // Vista 2
    /*ConstraintLayout {
        val (
            titleRef,
            nameRef,
            lastNameRef,
            sexRef,
            dateOfBirdRef,
            schoolGradeRef,
            title,
            telephone,
            address,
            email,
            countries,
            cities) = createRefs()

        PersonalDataActivity(
            titleRef = titleRef,
            nameRef = nameRef,
            lastNameRef = lastNameRef,
            sexRef = sexRef,
            dateOfBirdRef = dateOfBirdRef,
            schoolGradeRef = schoolGradeRef
        )
        ContactDataActivity(
            title = title,
            telephone = telephone,
            address = address,
            email = email,
            countries = countries,
            cities = cities
        )
    }*/
}


@Preview(showBackground = true, showSystemUi = true)
@ExperimentalComposeUiApi
@Composable
fun GreetingPreview() {
    Main()
}

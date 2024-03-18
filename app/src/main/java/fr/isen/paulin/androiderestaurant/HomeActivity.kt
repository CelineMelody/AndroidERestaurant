package fr.isen.paulin.androiderestaurant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.paulin.androiderestaurant.ui.theme.AndroidERestaurantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidERestaurantTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting()
                }
            }
        }
    }
}



@Composable
fun Greeting( modifier: Modifier = Modifier) {
    Column {
        Row {
            Column (
                modifier = modifier.padding(16.dp), // Ajout de padding
                verticalArrangement = Arrangement.Top, // Espacement vertical
                horizontalAlignment = Alignment.Start // Alignement horizontal au début
            ){
                Text(
                    text = "Bienvenue ",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    color = Color(0xFFFF8000) ,
                    modifier = modifier
                )
                Text(
                    text = "chez ",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    color = Color(0xFFFF8000) ,
                    modifier = modifier
                )
                Text(
                    text = "DroidRestaurant ",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    //fontStyle = FontStyle.,
                    color = Color(0xFFFF8000) ,
                    modifier = modifier
                )
            }
            Image(painter = painterResource(id = R.drawable.android_cuisine), contentDescription="")
        }
        // Ajout d'espace entre le message et les boutons
        Spacer(modifier = Modifier.height(32.dp))
        // Boutons
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center, // Centrer verticalement
            horizontalAlignment = Alignment.CenterHorizontally // Centrer horizontalement
        ) {

            // Textes cliquables
            Text(
                text = "Entrées",
                fontSize = 24.sp,
                color = Color(0xFFFF8000) ,
                modifier = Modifier.padding(vertical = 4.dp)
                    .clickable {  /*showToast(context,"Entrées")*/ }// Ajouter un padding vertical pour espacer les textes
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
            Text(
                text = "Plats",
                color = Color(0xFFFF8000),
                fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 4.dp)
                    .clickable { /*showToast(context,"Plats") */}// Ajouter un padding vertical pour espacer les textes
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
            Text(
                text = "Desserts",
                color = Color(0xFFFF8000),
                fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 4.dp)
                    .clickable {/* showToast(context,message = "Desserts") */}// Ajouter un padding vertical pour espacer les textes
            )

        }
    }
    }


//@Composable
/*fun showToast(context: Context, message: String) {
    LaunchedEffect(Unit) {
        //val context = LocalContext.current
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}*/

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidERestaurantTheme {
        Greeting()

    }
}
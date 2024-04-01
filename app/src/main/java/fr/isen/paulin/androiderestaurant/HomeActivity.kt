package fr.isen.paulin.androiderestaurant

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeComponent { message ->
                        navigateToCategory(message)
                    }
                }
            }
        }
    }
    private fun navigateToCategory(category: String) {
        val intent = Intent(this@MainActivity, CategoryActivity::class.java).apply {
            putExtra("category", category)
        }
        startActivity(intent)
    }

    /*fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }*/

}

@Composable
fun HomeComponent(navigateToCategory: (String) -> Unit) {
    val playfairDisplay = FontFamily(Font(R.font.playfairdisplaybold))
    val merriweather = FontFamily(Font(R.font.merriweathersansregular))
    Column {
        // Utiliser Row pour placer le texte et l'image sur la même rangée
        Row(
            // Bandeau avec fond blanc
            modifier = Modifier
                .background(Color.White) // Fond blanc pour le bandeau
                .padding(8.dp), // Padding autour du texte dans le bandeau
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier.padding(16.dp), // Ajout de padding
                verticalArrangement = Arrangement.Top, // Espacement vertical
                horizontalAlignment = Alignment.Start // Alignement horizontal au début
            ) {


                // Vos Textes
                Text(
                    text = "Bienvenue ",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    color = Color(0xFFFF8000),
                    fontFamily = playfairDisplay
                )
                Text(
                    text = "chez ",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    color = Color(0xFFFF8000),
                    fontFamily = playfairDisplay
                )
                Text(
                    text = "DroidRestaurant ",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    color = Color(0xFFFF8000),
                    fontFamily = playfairDisplay
                )
            }
            Spacer(modifier = Modifier.weight(1f)) // Espaceur pour pousser l'image vers la droite
            Image(
                painter = painterResource(id = R.drawable.android_cuisine),
                contentDescription = "",
                modifier = Modifier.size(100.dp) // Ajustez la taille selon le besoin
            )
        }
        // Ajout d'espace entre le message et les boutons
        Spacer(modifier = Modifier.height(100.dp))
        // Boutons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center, // Centrer verticalement
            horizontalAlignment = Alignment.CenterHorizontally // Centrer horizontalement
        ) {

            // Textes cliquables
            Text(
                text = "Entrées",
                fontSize = 30.sp,
                color = Color(0xFFFF8000),
                fontFamily = merriweather,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .clickable {
                        navigateToCategory("Entrées")

                    }// Ajouter un padding vertical pour espacer les textes
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
            Text(
                text = "Plats",
                color = Color(0xFFFF8000),
                fontSize = 30.sp,
                fontFamily = merriweather,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .clickable {
                        navigateToCategory("Plats")
                        /*coroutineScope.launch {
                            showToast("Plats")
                        }*/
                    }// Ajouter un padding vertical pour espacer les textes
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
            Text(
                text = "Desserts",
                color = Color(0xFFFF8000),
                fontSize = 30.sp,
                fontFamily = merriweather,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .clickable {
                        navigateToCategory("Desserts")
                        /*coroutineScope.launch {
                            showToast("Desserts")
                        }*/
                    }// Ajouter un padding vertical pour espacer les textes
            )

        }
    }
}



/*@Composable
fun HomeComponent(navigateToCategory: (String) -> Unit) {

    Column {
        Row {
            Column(
                modifier = Modifier.padding(16.dp), // Ajout de padding
                verticalArrangement = Arrangement.Top, // Espacement vertical
                horizontalAlignment = Alignment.Start // Alignement horizontal au début
            ) {
                Text(
                    text = "Bienvenue ",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    color = Color(0xFFFF8000),
                    modifier = Modifier
                )
                Text(
                    text = "chez ",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    color = Color(0xFFFF8000),
                    modifier = Modifier
                )
                Text(
                    text = "DroidRestaurant ",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    //fontStyle = FontStyle.,
                    color = Color(0xFFFF8000),
                    modifier = Modifier
                )
            }
            Image(
                painter = painterResource(id = R.drawable.android_cuisine),
                contentDescription = ""
            )
        }
        // Ajout d'espace entre le message et les boutons
        Spacer(modifier = Modifier.height(32.dp))
        // Boutons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center, // Centrer verticalement
            horizontalAlignment = Alignment.CenterHorizontally // Centrer horizontalement
        ) {

            // Textes cliquables
            Text(
                text = "Entrées",
                fontSize = 24.sp,
                color = Color(0xFFFF8000),
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .clickable {
                        navigateToCategory("Entrées")

                    }// Ajouter un padding vertical pour espacer les textes
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
            Text(
                text = "Plats",
                color = Color(0xFFFF8000),
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .clickable {
                        navigateToCategory("Plats")
                        /*coroutineScope.launch {
                            showToast("Plats")
                        }*/
                    }// Ajouter un padding vertical pour espacer les textes
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
            Text(
                text = "Desserts",
                color = Color(0xFFFF8000),
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .clickable {
                        navigateToCategory("Desserts")
                        /*coroutineScope.launch {
                            showToast("Desserts")
                        }*/
                    }// Ajouter un padding vertical pour espacer les textes
            )

        }
    }
}*/



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidERestaurantTheme {
        HomeComponent {}
    }
}
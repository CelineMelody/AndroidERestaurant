package fr.isen.paulin.androiderestaurant

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.gson.Gson
import fr.isen.paulin.androiderestaurant.model.Ingredients
import fr.isen.paulin.androiderestaurant.model.Items
import fr.isen.paulin.androiderestaurant.ui.theme.AndroidERestaurantTheme
import java.io.File
import java.io.FileWriter

val Orange = Color(0xFFFF8000)

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dish = intent.getSerializableExtra("dish") as? Items

        setContent {
            AndroidERestaurantTheme {
                dish?.let {
                    Surface(color = MaterialTheme.colorScheme.background) {
                        DishDetailScreen(dish = it)
                    }
                }
            }
        }
    }
}

@Composable
fun DishDetailScreen(dish: Items) {
    val context = LocalContext.current
    val quantity = remember { mutableStateOf(1) }
    val pricePerDish = dish.prices.first().price?.toFloatOrNull() ?: 0f
    val showConfirmationDialog = remember { mutableStateOf(false) }
    val playfairDisplay = FontFamily(Font(R.font.playfairdisplaybold))
    val merriweather = FontFamily(Font(R.font.merriweathersansregular))


    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = dish.nameFr ?: "Détails du plat",
                modifier = Modifier.padding(bottom = 16.dp),
                fontFamily = merriweather,
                fontSize = 24.sp
            )
            ImageCarousel(images = dish.images)
            Spacer(modifier = Modifier.height(16.dp))
            IngredientList(ingredients = dish.ingredients)
            Spacer(modifier = Modifier.height(16.dp))
            QuantityCounter(quantity = quantity.value,
                onIncrease = { if (quantity.value < 99) quantity.value++ },
                onDecrease = { if (quantity.value > 1) quantity.value-- })
        }

        Button(
            onClick = {
                val panierItem = PanierItem(
                    nom = dish.nameFr ?: "Inconnu",
                    quantite = quantity.value,
                    prixTotal = quantity.value * pricePerDish
                )
                sauvegarderPanier(context, panierItem)
                showConfirmationDialog.value = true
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Orange)
        ) {
            Text("TOTAL: ${quantity.value * pricePerDish} €")
        }

        if (showConfirmationDialog.value) {
            AlertDialog(onDismissRequest = { showConfirmationDialog.value = false },
                confirmButton = {
                    Button(onClick = { showConfirmationDialog.value = false }) {
                        Text("OK")
                    }
                },
                title = { Text("Confirmation") },
                text = { Text("Article ajouté au panier.") })
        }
    }
}

@Composable
fun ImageCarousel(images: List<String>) {
    LazyRow(modifier = Modifier.height(200.dp)) {
        items(images) { imageUrl ->
            AsyncImage(
                model = imageUrl,
                contentDescription = "Image du plat",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(width = 200.dp, height = 200.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun IngredientList(ingredients: List<Ingredients>) {
    val ingredientNames = ingredients.joinToString(separator = ", ") { it.nameFr ?: "" }
    Text(text = "Ingredients: $ingredientNames", modifier = Modifier.padding(bottom = 8.dp))
}

@Composable
fun QuantityCounter(quantity: Int, onIncrease: () -> Unit, onDecrease: () -> Unit) {
    Row(modifier = Modifier.padding(16.dp)) {
        Button(
            onClick = onDecrease,
            modifier = Modifier.size(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Orange)
        ) { Text("-") }
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = quantity.toString(), modifier = Modifier.align(Alignment.CenterVertically))
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = onIncrease,
            modifier = Modifier.size(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Orange)
        ) { Text("+") }
    }
}

fun sauvegarderPanier(context: Context, panierItem: PanierItem) {
    val gson = Gson()
    val panierJson = gson.toJson(panierItem)
    val fichier = File(context.filesDir, "panier.json")
    FileWriter(fichier, true).apply {
        write("$panierJson\n")
        close()
    }
}

data class PanierItem(val nom: String, val quantite: Int, val prixTotal: Float)





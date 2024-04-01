package fr.isen.paulin.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.paulin.androiderestaurant.model.DataResult
import fr.isen.paulin.androiderestaurant.model.Items
import fr.isen.paulin.androiderestaurant.ui.theme.AndroidERestaurantTheme
import org.json.JSONObject

class CategoryActivity : ComponentActivity() {
    //private lateinit var queue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val category = intent.getStringExtra("category") ?: ""

        val fakeDishes = when (category) {
            "Entrées" -> resources.getStringArray(R.array.fake_starters)
            "Plats" -> resources.getStringArray(R.array.fake_dish)
            "Desserts" -> resources.getStringArray(R.array.fake_desert)
            else -> resources.getStringArray(R.array.fake_dish)
        }
        // Utilisez la catégorie pour afficher les plats correspondants
        setContent {
            AndroidERestaurantTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val itemsToDisplay = remember {
                        mutableStateListOf<Items>()
                    }
                    fetchData(category, itemsToDisplay)
                    CategoryComponent(category, itemsToDisplay) { dish ->
                        onDishClicked(dish)
                    }
                }
            }
        }
    }


    private fun onDishClicked(dish: Items) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("dish", dish)
        startActivity(intent)
    }


    //maj l'etat de notre composant de facon asynchrone avec state et remember
    private fun fetchData(category: String, items: MutableList<Items>) {
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val param = JSONObject()
        param.put("id_shop", "1")
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, param,
            { response ->
                val result = Gson().fromJson(response.toString(), DataResult::class.java)
                val dishesFromCategory =
                    result.data.find { it.nameFr == category }?.items ?: emptyList()


                items.addAll(dishesFromCategory)

                Log.d("CategoryActivity", "result : $response")
                //va sur json2kt fr.isen.paulin.androiderestaurant.model  DataResult
            }, { error ->
                Log.e("CategoryActivity", "error : $error")
            }
        )
        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }


}

@Composable
fun CategoryComponent(category: String, dishes: List<Items>, onDishClicked: (Items) -> Unit) {
    val playfairDisplay = FontFamily(Font(R.font.playfairdisplaybold))
    val merriweather = FontFamily(Font(R.font.merriweathersansregular))
    Column {
        Text(
            text = "~ $category ~",
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            color = Color(0xFFFF8000),
            fontFamily = merriweather,
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth()
        )
        LazyColumn(Modifier.padding(24.dp)) {
            items(dishes) { dish ->
                Row {
                    AsyncImage(
                        model = dish.images.last(),
                        contentDescription = null,
                        Modifier
                            .size(100.dp)
                            //.clip(CircleShape)
                            //.clip(RoundedCornerShape(10.dp))
                            .padding(end = 16.dp)
                            //.clickable { onDishClicked(dish)
                    )
                    Column {
                        Text(text = dish.nameFr ?: "",
                            Modifier
                                .padding(0.dp, 8.dp)
                                .clickable { onDishClicked(dish) }
                        )
                        Text(
                            "${dish.prices.first().price} €",
                            Modifier
                                .padding(0.dp, 8.dp)
                                .clickable { onDishClicked(dish) }
                        )
                    }


                }
                //CoilImage(imageUrl = dish.imageUrl, modifier = Modifier.size(50.dp))
            }
        }
    }

}


/*private fun fetchDishes(category: String) {
    val url = "http://test.api.catering.bluecodegames.com/menu"
    val params = JSONObject()
    params.put("id_shop", 1) // Passer l'objet "id_shop" avec la valeur "1"

    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.POST, url, params,
        Response.Listener<JSONObject> { response ->
            // Traitement de la réponse JSON pour extraire les plats
            val dishes = parseDishes(response, category)

            // Afficher les plats dans l'interface utilisateur
            setContent {
                AndroidERestaurantTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        CategoryComponent(category, dishes) { dish ->
                            onDishClicked(dish)
                        }
                    }
                }
            }
        },
        Response.ErrorListener { error ->
            // Gérer les erreurs de la requête
            error.printStackTrace()
        })

    // Ajouter la requête à la file d'attente de Volley
    queue.add(jsonObjectRequest)
}*/
/*private fun parseDishes(response: JSONObject, category: String): List<String> {
    val dishesList = mutableListOf<String>()

    // Vérifier si la réponse contient les données attendues
    if (response.has("data")) {
        val dataObject = response.getJSONObject("data")

        // Extraire les plats de la catégorie spécifiée
        if (dataObject.has(category)) {
            val categoryArray = dataObject.getJSONArray(category)

            // Parcourir les éléments du tableau de plats
            for (i in 0 until categoryArray.length()) {
                val dish = categoryArray.getString(i)
                dishesList.add(dish)
            }
        }
    }

    return dishesList
}*/
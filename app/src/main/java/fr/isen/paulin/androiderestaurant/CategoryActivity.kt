package fr.isen.paulin.androiderestaurant

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.paulin.androiderestaurant.ui.theme.AndroidERestaurantTheme

class CategoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_category)
        val category = intent.getStringExtra("category") ?: ""

        val fakeDishes = when(category){
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
                    CategoryComponent(category, fakeDishes.asList()){ dish ->
                        onDishClicked(dish)
                    }
                }
            }
        }
    }
    private fun onDishClicked(dish: String){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("dish",dish)
        startActivity(intent)
    }
}

@Composable
fun CategoryComponent(category: String, dishes: List<String>, onDishClicked: (String) -> Unit) {
    Column {
        Text(
            text = "Hello $category!",
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(32.dp)
                .fillMaxWidth()
        )
    }
    LazyColumn (Modifier.padding(24.dp)){
        items(dishes) {dish ->
            Text(text = dish,
                Modifier.padding(0.dp, 8.dp)
                    .clickable { onDishClicked(dish) })
        }
    }
}

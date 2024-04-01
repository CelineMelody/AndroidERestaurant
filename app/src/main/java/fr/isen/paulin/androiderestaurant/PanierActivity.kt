package fr.isen.paulin.androiderestaurant

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import fr.isen.paulin.androiderestaurant.ui.theme.AndroidERestaurantTheme
import java.io.File
import java.io.FileWriter

// Importations nécessaires...

class PanierActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidERestaurantTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    PanierScreen()
                }
            }
        }
    }
}

@Composable
fun PanierScreen() {
    val context = LocalContext.current
    val panierItems = lirePanier(context)

    Column(modifier = Modifier.padding(16.dp)) {
        /*LazyColumn {
            items(panierItems) { item ->
                PanierItemRow(panierItem = item, onSupprimer = {
                    supprimerItemDuPanier(context, item)
                })
            }
        }*/
        Button(onClick = { /* Logique pour passer la commande */ },
            modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Passer la commande")
        }
    }
}

// Affiche chaque item du panier avec un bouton pour supprimer
@Composable
fun PanierItemRow(panierItem: PanierItem, onSupprimer: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = "${panierItem.nom} x${panierItem.quantite} - ${panierItem.prixTotal}€", modifier = Modifier.weight(1f))
        IconButton(onClick = onSupprimer) {
            Icon(Icons.Default.Delete, contentDescription = "Supprimer")
        }
    }
}

// Lire le panier depuis le fichier JSON
fun lirePanier(context: Context): List<PanierItem> {
    val fichier = File(context.filesDir, "panier.json")
    if (!fichier.exists()) {
        return emptyList()
    }
    val gson = Gson()
    return fichier.readLines().mapNotNull { line ->
        gson.fromJson(line, PanierItem::class.java)
    }
}

// Supprimer un item du panier et mettre à jour le fichier
fun supprimerItemDuPanier(context: Context, itemASupprimer: PanierItem) {
    val panierItems = lirePanier(context).toMutableList()
    panierItems.remove(itemASupprimer)
    // Mettre à jour le fichier panier.json
    val gson = Gson()
    val fichier = File(context.filesDir, "panier.json")
    FileWriter(fichier, false).use { writer ->
        panierItems.forEach { item ->
            writer.write(gson.toJson(item) + "\n")
        }
    }
    // Rafraîchir l'écran du panier pour afficher les modifications
}

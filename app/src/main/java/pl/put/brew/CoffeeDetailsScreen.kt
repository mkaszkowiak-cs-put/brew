package pl.put.brew

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import org.openapitools.client.models.Coffee

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CoffeeDetailsScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    coffee: Coffee
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("coffee-rate/${coffee.id}") },
                modifier = Modifier.offset(x = (-8).dp, y = (-8).dp)
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        },
    ) {
        ScrollableColumn {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(
                        24.dp
                    )

            ) {
                Image(
                    rememberImagePainter(coffee.imageUrl),
                    contentDescription = coffee.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(360.dp),
                    contentScale = ContentScale.Crop,
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = coffee.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = coffee.manufacturer,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = getRatingString(coffee.rating),
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "\uD83C\uDDF0\uD83C\uDDEA ${coffee.country}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
                Text(
                    text = "☕ ${coffee.cuppingScore}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
                Text(
                    text = "⚙ ${coffee.processing}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "\uD83D\uDE0B ${coffee.notes}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Najnowsze recenzje",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
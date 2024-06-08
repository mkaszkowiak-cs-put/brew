package pl.put.brew

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun CoffeeRatingSuccessScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(vertical = 16.dp)) {
        Text(
            text = "Juz oceniles kawke",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Button(onClick = { navController.navigate("coffee-details/1") }) {
            Text("Wroc do widoku szczegolow")
        }
    }
}
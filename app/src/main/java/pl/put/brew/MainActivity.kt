package pl.put.brew

import CoffeeDetailsScreen
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.put.brew.ui.theme.BrewTheme

sealed class Screen(val route: String) {
    data object CoffeeListScreen : Screen("coffee-list")
    data object CoffeeDetailsScreen : Screen("coffee-details/{coffeeId}")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            BrewTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController, startDestination = Screen.CoffeeListScreen.route) {
                        composable(Screen.CoffeeListScreen.route) {
                            CoffeeListScreen(navController = navController, modifier = Modifier.padding(innerPadding))
                        }
                        composable(Screen.CoffeeDetailsScreen.route) {
                            CoffeeDetailsScreen(modifier = Modifier.padding(innerPadding))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BrewTheme {
        Text("XD")
    }
}
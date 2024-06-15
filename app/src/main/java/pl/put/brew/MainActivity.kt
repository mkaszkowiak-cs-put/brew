package pl.put.brew

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.openapitools.client.apis.DefaultApi
import org.openapitools.client.infrastructure.ApiClient
import org.openapitools.client.models.Coffee
import org.openapitools.client.models.Review
import pl.put.brew.ui.theme.BrewTheme

sealed class Screen(val route: String) {
    data object AuthorizationScreen : Screen("home")
    data object CoffeeListScreen : Screen("coffee-list")
    data object CoffeeDetailsScreen : Screen("coffee-details/{coffeeId}")
    data object CoffeeRatingScreen : Screen("coffee-rate/{coffeeId}")
    data object CoffeeRatingSuccessScreen : Screen("coffee-rate-success/{coffeeId}")
}

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val userModel: UserModel = viewModel()

            var coffeeList by rememberSaveable { mutableStateOf<List<Coffee>>(emptyList()) }
            var errorMessage by rememberSaveable { mutableStateOf<String?>(null) }

            fun addReviewToCoffee(coffeeId: Int, review: Review) {
                coffeeList = coffeeList.map { coffee ->
                    if (coffee.id == coffeeId) {
                        val updatedReviews = coffee.reviews?.toMutableList()?.apply {
                            add(0, review)
                        }
                        coffee.copy(reviews = updatedReviews)
                    } else {
                        coffee
                    }
                }
            }

            LaunchedEffect(Unit) {
                val apiClient = ApiClient()
                val api = apiClient.createService(DefaultApi::class.java)

                val call = api.getCoffeeCoffeeGet()

                val apiService = ApiService<List<Coffee>>()
                apiService.makeApiCall(call, object : ApiCallback<List<Coffee>> {
                    override fun onSuccess(result: List<Coffee>) {
                        coffeeList = result
                    }

                    override fun onError(t: Throwable) {
                        errorMessage = t.message
                    }
                })
            }

            BrewTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController, startDestination = Screen.AuthorizationScreen.route) {
                        composable(Screen.AuthorizationScreen.route) {
                            AuthorizationScreen(
                                navController = navController,
                                userModel = userModel,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable(Screen.CoffeeListScreen.route) {
                            CoffeeListScreen(
                                navController = navController,
                                coffeeList = coffeeList,
                                userModel = userModel,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable(Screen.CoffeeDetailsScreen.route) { backStackEntry ->
                            val coffeeId = backStackEntry.arguments?.getString("coffeeId")
                            val coffee = coffeeList.find { it.id == coffeeId?.toInt() }

                            if (coffee != null) {
                                CoffeeDetailsScreen(
                                    navController = navController,
                                    modifier = Modifier.padding(innerPadding),
                                    coffee = coffee,
                                )
                            } else {
                                Text("Coffee with ID $coffeeId not found.")
                            }
                        }
                        composable(Screen.CoffeeRatingScreen.route) { backStackEntry ->
                            val coffeeId = backStackEntry.arguments?.getString("coffeeId")
                            val coffee = coffeeList.find { it.id == coffeeId?.toInt() }

                            if (coffee != null) {
                                CoffeeRatingScreen(
                                    navController = navController,
                                    modifier = Modifier.padding(innerPadding),
                                    coffee = coffee,
                                    userModel = userModel,
                                    addReviewToCoffee = { coffeeId, review ->
                                        addReviewToCoffee(
                                            coffeeId,
                                            review
                                        )
                                    }
                                )
                            } else {
                                Text("Coffee with ID $coffeeId not found.")
                            }
                        }
                        composable(Screen.CoffeeRatingSuccessScreen.route) { backStackEntry ->
                            val coffeeId = backStackEntry.arguments?.getString("coffeeId")
                            val coffee = coffeeList.find { it.id == coffeeId?.toInt() }

                            if (coffee != null) {
                                CoffeeRatingSuccessScreen(
                                    navController = navController,
                                    modifier = Modifier.padding(innerPadding),
                                    coffee = coffee
                                )
                            } else {
                                Text("Coffee with ID $coffeeId not found.")
                            }
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
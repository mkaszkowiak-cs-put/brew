package pl.put.brew

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.openapitools.client.apis.DefaultApi
import org.openapitools.client.infrastructure.ApiClient
import org.openapitools.client.models.Coffee
import org.openapitools.client.models.Review
import org.openapitools.client.models.ReviewCreate
import java.time.OffsetDateTime

class ReviewModel(val coffeeId: Int, val user: String) : ViewModel() {
    var review by mutableStateOf("")
    var rating by mutableFloatStateOf(1f)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CoffeeRatingScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    coffee: Coffee,
    userModel: UserModel,
    addReviewToCoffee: (Int, Review) -> Unit
) {
    // TODO: Add empty validation
    val reviewModel = remember { ReviewModel(coffeeId = coffee.id, user = userModel.name) }
    var showError by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    fun addReview() {
        coroutineScope.launch {
            val apiClient = ApiClient()
            val api = apiClient.createService(DefaultApi::class.java)
            val reviewCreate = ReviewCreate(
                rating = reviewModel.rating.toBigDecimal(),
                coffeeId = reviewModel.coffeeId,
                date = OffsetDateTime.now(),
                user = reviewModel.user,
                review = reviewModel.review
            )

            val call = api.createReviewReviewPost(reviewCreate)

            val apiService = ApiService<Review>()
            apiService.makeApiCall(call, object : ApiCallback<Review> {
                override fun onSuccess(result: Review) {
                    addReviewToCoffee(
                        reviewModel.coffeeId, result
                    )
                }

                override fun onError(t: Throwable) {
                    println("Error: ${t.message}")
                }
            })
        }

        navController.navigate("coffee-rate-success/${coffee.id}")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = coffee.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(32.dp))
        TextField(
            value = reviewModel.review,
            onValueChange = {
                reviewModel.review = it
            },
            label = { Text("Treść opinii") },
            minLines = 3,
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (reviewModel.review.isBlank()) {
                        showError = true
                    } else {
                        navController.navigate("coffee-list")
                    }
                }
            ),
            isError = showError,
        )
        if (showError) {
            Text(
                text = "Daj znać co sądzisz o tej kawie!",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text("Poziom kawowej euforii")
        Slider(
            value = reviewModel.rating,
            onValueChange = { reviewModel.rating = it },
            steps = 3,
            valueRange = 1f..5f,
        )
        Spacer(modifier = Modifier.height(50.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Pisząc recenzję, możesz wspomóc się pytaniami:\n" +
                        "\n" +
                        "  • jak zaparzyłæś kawę? (metoda, stopień zmielenia, temperatura wody)\n" +
                        "  • kiedy otworzyłæś paczkę? \n" +
                        "  • jakie odczuwasz aromaty?",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray,
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            if (reviewModel.review.isBlank()) {
                showError = true
            } else {
                addReview()
            }
        }) {
            Text("Ocen kawke")
        }
    }
}
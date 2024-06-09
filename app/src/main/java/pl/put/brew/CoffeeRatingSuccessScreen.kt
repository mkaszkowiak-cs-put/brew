package pl.put.brew

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.openapitools.client.apis.DefaultApi
import org.openapitools.client.infrastructure.ApiClient
import org.openapitools.client.models.Review
import org.openapitools.client.models.ReviewCreate
import java.math.BigDecimal
import java.time.OffsetDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CoffeeRatingSuccessScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    LaunchedEffect(Unit) {
        val apiClient = ApiClient()
        val api = apiClient.createService(DefaultApi::class.java)
        val reviewCreate = ReviewCreate(
            rating = BigDecimal(5),
            coffeeId = 2,
            date = OffsetDateTime.now(),
            user = "Owca WK",
            review = "To najlepsza kawa jaką piłem przez API w życiu!"
        )

        val call = api.createReviewReviewPost(reviewCreate)

        val apiService = ApiService<Review>()
        apiService.makeApiCall(call, object : ApiCallback<Review> {
            override fun onSuccess(result: Review) {
                println(result)
            }

            override fun onError(t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
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
package pl.put.brew

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.openapitools.client.models.Coffee

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CoffeeRatingSuccessScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    coffee: Coffee
) {
//    LaunchedEffect(Unit) {
//        val apiClient = ApiClient()
//        val api = apiClient.createService(DefaultApi::class.java)
//        val reviewCreate = ReviewCreate(
//            rating = BigDecimal(5),
//            coffeeId = 2,
//            date = OffsetDateTime.now(),
//            user = "Owca WK",
//            review = "To najlepsza kawa jaką piłem przez API w życiu!"
//        )
//
//        val call = api.createReviewReviewPost(reviewCreate)
//
//        val apiService = ApiService<Review>()
//        apiService.makeApiCall(call, object : ApiCallback<Review> {
//            override fun onSuccess(result: Review) {
//                println(result)
//            }
//
//            override fun onError(t: Throwable) {
//                println("Error: ${t.message}")
//            }
//        })
//    }

    val textContent = buildAnnotatedString {
        append("Dziękujemy za ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("KAWA")
        }
        append("ł dobrej opinii ;)")
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.coffee),
            contentDescription = "Brew - Po prostu kawa",
            modifier = Modifier.height(120.dp),
            contentScale = ContentScale.FillHeight
        )
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = textContent,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("coffee-details/${coffee.id}") }) {
            Text("Cała kawa po mojej stronie :-)")
        }
        Spacer(modifier = Modifier.weight(1f))
        ZylaCard()
    }
}

@Composable
fun ZylaCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        )
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Image Layer
            Image(
                painter = painterResource(id = R.drawable.zyla),
                contentDescription = "Person",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 16.dp)
                    .height(200.dp)
                    .width(200.dp)
                    .graphicsLayer {
                        translationX = (-120).toFloat() // Shift image outside the bounds
                    }
            )

            // Content Layer
            Column(
                modifier = Modifier
                    .padding(
                        top = 40.dp,
                        bottom = 64.dp,
                        start = 100.dp
                    ), // Adjust the start padding to accommodate the image
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Nie bądź źyla!")
                Text("Podziel się kawą z innymi!", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(24.dp))
                Button(onClick = { /*TODO*/ }) {
                    Text("Z chęcią :-)")
                }
            }
        }
    }
}

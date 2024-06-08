package pl.put.brew

import android.util.Log
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
import org.openapitools.client.models.Coffee
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CoffeeDetailsScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    LaunchedEffect(Unit) {
        // Place your logic here
        val apiClient = ApiClient()
        val mapObjectService = apiClient.createService(DefaultApi::class.java)
        val call = mapObjectService.getCoffeeCoffeeGet()

        call.enqueue(object : Callback<List<Coffee>> {
            override fun onFailure(
                call: Call<List<Coffee>>,
                t: Throwable
            ) {
                Log.v("retrofit", "call failed")
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<Coffee>>,
                response: Response<List<Coffee>>
            ) {
                if (response.isSuccessful) {
                    val mapModel = response.body()
                    println(mapModel)
                } else {
                    val statusCode = response.code()
                    println("Http Code: $statusCode")
                }
            }
        })
    }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(vertical = 16.dp)) {
        Text(
            text = "Tu masz szczegoly kawki",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Button(onClick = { navController.navigate("coffee-rate/1") }) {
            Text("Ocen kawke")
        }
    }
}
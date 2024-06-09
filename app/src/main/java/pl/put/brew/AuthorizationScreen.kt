package pl.put.brew

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AuthorizationScreen(
    navController: NavHostController,
    userModel: UserModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.brand),
            contentDescription = "Brew - Po prostu kawa",
            modifier = Modifier.height(80.dp),
            contentScale = ContentScale.FillHeight
        )
        Spacer(modifier = Modifier.height(48.dp))
        TextField(
            value = userModel.name,
            onValueChange = { userModel.name = it },
            label = { Text("Nazwa użytkownika") }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.navigate("coffee-list") }) {
            Text("Zaloguj się!")
        }
    }
}
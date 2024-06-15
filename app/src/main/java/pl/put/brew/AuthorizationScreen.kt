package pl.put.brew

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AuthorizationScreen(
    navController: NavHostController,
    userModel: UserModel,
    modifier: Modifier = Modifier
) {
    var showError by rememberSaveable { mutableStateOf(false) }

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
            onValueChange = {
                userModel.name = it
                showError = false
            },
            label = { Text("Nazwa użytkownika") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (userModel.name.isBlank()) {
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
                text = "Nazwa użytkownika nie może być pusta",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            if (userModel.name.isBlank()) {
                showError = true // Show error when input is empty
            } else {
                navController.navigate("coffee-list")
            }
        }) {
            Text("Zaloguj się!")
        }
    }
}
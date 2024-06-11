package pl.put.brew

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import org.openapitools.client.models.Coffee

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CoffeeListScreen(
    navController: NavHostController,
    userModel: UserModel,
    coffeeList: List<Coffee>,
    modifier: Modifier = Modifier
) {
    var filteredCoffeeList by remember { mutableStateOf(coffeeList) }

    fun onItemClick(id: Int) {
        navController.navigate("coffee-details/$id")
    }

    fun onSearch(name: String) {
        if (name.isBlank()) {
            filteredCoffeeList = coffeeList
            return;
        }

        filteredCoffeeList = coffeeList.filter { it.name.contains(name, ignoreCase = true) }
    }

    ScrollableColumn() {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Hej ${userModel.name}, czy to pora na kawę?",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            CoffeeSearchBar(onSearch = { onSearch(it) })
            Spacer(modifier = Modifier.height(32.dp))
            CoffeeList(items = filteredCoffeeList) { id -> onItemClick(id = id) }
        }
    }
}

@Composable
fun CoffeeList(
    items: List<Coffee>,
    onItemClick: (Int) -> Unit
) {
    items.forEach { coffee ->
        CoffeeCard(coffee, onItemClick)
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun CoffeeCard(coffee: Coffee, onItemClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(coffee.id) }),
        border = BorderStroke(1.dp, Color.LightGray),
        shape = MaterialTheme.shapes.small,
    ) {
        Image(
            rememberImagePainter(coffee.imageUrl),
            contentDescription = coffee.name,
            modifier = Modifier
                .height(164.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 24.dp
            )
        ) {
            Text(
                text = getRatingString(coffee.rating),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = coffee.name,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = coffee.manufacturer,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
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
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "\uD83D\uDE0B ${coffee.notes}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
@OptIn(
    ExperimentalMaterial3Api::class
)
fun CoffeeSearchBar(onSearch: (String) -> Unit) {
    var text by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        Modifier
            .fillMaxSize()
    ) {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = text,
                    onQueryChange = {
                        text = it
                        onSearch(it)
                    },
                    expanded = false,
                    onExpandedChange = {},
                    placeholder = { Text("Na jaką kawę masz dzisiaj ochotę?") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    onSearch = { it ->
                        onSearch(it)
                        keyboardController?.hide()
                    },
                )
            },
            expanded = false,
            onExpandedChange = {}
        ) {
            Text(text = "XD")
        }
    }
}

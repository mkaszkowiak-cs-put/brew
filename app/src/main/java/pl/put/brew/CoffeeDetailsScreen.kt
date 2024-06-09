package pl.put.brew

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import org.openapitools.client.models.Coffee
import org.openapitools.client.models.Review
import java.time.Duration
import java.time.OffsetDateTime

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CoffeeDetailsScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    coffee: Coffee
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("coffee-rate/${coffee.id}") },
                modifier = Modifier.offset(x = (-8).dp, y = (-8).dp)
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        },
    ) {
        ScrollableColumn {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(
                        24.dp
                    )
            ) {
                CoffeeInformation(coffee = coffee)
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Najnowsze recenzje",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                coffee.reviews?.forEach { review ->
                    Spacer(modifier = Modifier.height(24.dp))
                    ReviewCard(review = review)
                }
            }
        }
    }
}

@Composable
fun CoffeeInformation(coffee: Coffee) {
    Image(
        rememberImagePainter(coffee.imageUrl),
        contentDescription = coffee.name,
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp),
        contentScale = ContentScale.Crop,
    )
    Text(
        text = coffee.name,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
    )
    Text(
        text = coffee.manufacturer,
        style = MaterialTheme.typography.bodyMedium,
        color = Color.DarkGray
    )
    Spacer(Modifier.height(16.dp))
    Text(
        text = getRatingString(coffee.rating),
        style = MaterialTheme.typography.titleMedium,
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
    Spacer(modifier = Modifier.height(24.dp))
    Text(
        text = "\uD83D\uDE0B ${coffee.notes}",
        style = MaterialTheme.typography.bodyMedium,
        color = Color.DarkGray
    )
    Spacer(modifier = Modifier.height(40.dp))
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReviewCard(review: Review) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        border = BorderStroke(1.dp, Color.LightGray),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 24.dp
            )
        ) {
            Row {
                Image(
                    rememberImagePainter(review.imageUrl),
                    contentDescription = review.user,
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = review.user,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = getTimePassedString(review.date),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.DarkGray
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = getRatingString(review.rating),
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(Modifier.height(24.dp))
            Text(review.review)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getTimePassedString(time: OffsetDateTime): String {
    val now = OffsetDateTime.now()
    val duration = Duration.between(time, now)

    // Calculate number of days and weeks
    val daysPassed = duration.toDays()
    val weeksPassed = daysPassed / 7

    return when {
        duration.toMinutes() < 60 -> "Just now" // Less than an hour
        duration.toHours() < 24 -> "${duration.toHours()} hours ago" // Less than a day
        daysPassed < 7 -> "$daysPassed days ago" // Less than a week
        else -> "$weeksPassed weeks ago" // More than a week
    }
}
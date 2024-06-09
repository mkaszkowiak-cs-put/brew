package pl.put.brew

import java.math.BigDecimal

fun getRatingString(rating: BigDecimal?): String {
    if (rating == null) {
        return "Rating not available"
    }

    val fullStars = rating.toInt()
    val remainingStars = 5 - fullStars

    return "⭐".repeat(fullStars) + "★".repeat(remainingStars)
}
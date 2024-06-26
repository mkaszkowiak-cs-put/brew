/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package org.openapitools.client.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param rating 
 * @param user 
 * @param review 
 * @param date 
 * @param id 
 * @param coffeeId 
 * @param imageUrl 
 */


data class Review (

    @Json(name = "rating")
    val rating: java.math.BigDecimal,

    @Json(name = "user")
    val user: kotlin.String,

    @Json(name = "review")
    val review: kotlin.String,

    @Json(name = "date")
    val date: java.time.OffsetDateTime,

    @Json(name = "id")
    val id: kotlin.Int,

    @Json(name = "coffee_id")
    val coffeeId: kotlin.Int,

    @Json(name = "image_url")
    val imageUrl: kotlin.String? = null

) {


}


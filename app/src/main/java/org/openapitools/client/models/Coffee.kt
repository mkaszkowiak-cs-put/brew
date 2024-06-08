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

import org.openapitools.client.models.Review

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param id 
 * @param name 
 * @param manufacturer 
 * @param country 
 * @param cuppingScore 
 * @param processing 
 * @param roast 
 * @param notes 
 * @param reviews 
 * @param rating 
 * @param imageUrl 
 */


data class Coffee (

    @Json(name = "id")
    val id: kotlin.Int,

    @Json(name = "name")
    val name: kotlin.String,

    @Json(name = "manufacturer")
    val manufacturer: kotlin.String,

    @Json(name = "country")
    val country: kotlin.String,

    @Json(name = "cupping_score")
    val cuppingScore: kotlin.String,

    @Json(name = "processing")
    val processing: kotlin.String,

    @Json(name = "roast")
    val roast: kotlin.String,

    @Json(name = "notes")
    val notes: kotlin.String,

    @Json(name = "reviews")
    val reviews: kotlin.collections.List<Review>? = null,

    @Json(name = "rating")
    val rating: java.math.BigDecimal? = null,

    @Json(name = "image_url")
    val imageUrl: kotlin.String? = null

) {


}


package org.openapitools.client.apis

import org.openapitools.client.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Call
import okhttp3.RequestBody
import com.squareup.moshi.Json

import org.openapitools.client.models.Coffee
import org.openapitools.client.models.HTTPValidationError
import org.openapitools.client.models.Review
import org.openapitools.client.models.ReviewCreate

interface DefaultApi {
    /**
     * Create Review
     * 
     * Responses:
     *  - 200: Successful Response
     *  - 422: Validation Error
     *
     * @param reviewCreate 
     * @return [Call]<[Review]>
     */
    @POST("review")
    fun createReviewReviewPost(@Body reviewCreate: ReviewCreate): Call<Review>

    /**
     * Get Coffee
     * 
     * Responses:
     *  - 200: Successful Response
     *  - 422: Validation Error
     *
     * @param skip  (optional, default to 0)
     * @param limit  (optional, default to 100)
     * @return [Call]<[kotlin.collections.List<Coffee>]>
     */
    @GET("coffee")
    fun getCoffeeCoffeeGet(@Query("skip") skip: kotlin.Int? = 0, @Query("limit") limit: kotlin.Int? = 100): Call<kotlin.collections.List<Coffee>>

    /**
     * Read Root
     * 
     * Responses:
     *  - 200: Successful Response
     *
     * @return [Call]<[kotlin.Any]>
     */
    @GET("")
    fun readRootGet(): Call<kotlin.Any>

}

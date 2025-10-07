package com.marcomarais.welltrack.data.remote

import retrofit2.http.*

data class FoodDto(
    val barcode: String,
    val name: String,
    val calories: Int,
    val protein: Double? = null,
    val carbs: Double? = null,
    val fat: Double? = null
)
data class MealDto(val name: String, val calories: Int, val quantity: Int)
data class CreatedResponse(val id: String)

interface ApiService {
    @GET("foods/{barcode}")
    suspend fun getFood(@Path("barcode") barcode: String): FoodDto

    @POST("users/{uid}/meals")
    suspend fun logMeal(@Path("uid") uid: String, @Body body: MealDto): CreatedResponse
}

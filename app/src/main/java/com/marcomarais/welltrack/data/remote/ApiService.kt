package com.marcomarais.welltrack.data.remote

import retrofit2.http.*


data class MealDto(val name: String, val calories: Int, val quantity: Int)
data class CreatedResponse(val id: String)
data class MealLogRequest(
    val uid: String,
    val barcode: String,
    val name: String,
    val calories: Int
)

interface ApiService {
    @GET("foods/{barcode}")
    suspend fun getFood(@Path("barcode") barcode: String): FoodDto

    @POST("users/{uid}/meals")
    suspend fun logMeal(@Path("uid") uid: String, @Body body: MealDto): CreatedResponse
}

package com.marcomarais.welltrack.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodDto(
    @Json(name = "barcode") val barcode: String,
    @Json(name = "name") val name: String,
    @Json(name = "calories") val calories: Int,
    @Json(name = "protein") val protein: Double? = null,
    @Json(name = "carbs") val carbs: Double? = null,
    @Json(name = "fat") val fat: Double? = null
)

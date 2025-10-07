package com.marcomarais.welltrack.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.marcomarais.welltrack.data.remote.ApiService
import com.marcomarais.welltrack.data.remote.FoodDto
import com.marcomarais.welltrack.data.remote.MealDto

class MealRepository(
    private val api: ApiService,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    suspend fun fetchFood(barcode: String): FoodDto = api.getFood(barcode)

    suspend fun logMeal(food: FoodDto, quantity: Int) {
        val uid = auth.currentUser?.uid ?: error("Not logged in")
        val meal = MealDto(food.name, food.calories, quantity)
        api.logMeal(uid, meal) // REST insert
        firestore.collection("users").document(uid) // mirror to Firestore for demo
            .collection("meals").add(meal)
    }
}

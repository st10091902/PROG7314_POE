package com.marcomarais.welltrack.data

import com.google.firebase.auth.FirebaseAuth
import com.marcomarais.welltrack.data.local.MealDao
import com.marcomarais.welltrack.data.local.MealEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import java.time.Instant

class MealLocalRepository(
    private val mealDao: MealDao,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    private fun currentUid(): String? = auth.currentUser?.uid

    suspend fun logMealLocally(
        barcode: String,
        name: String,
        calories: Int
    ) {
        val uid = currentUid() ?: return

        val meal = MealEntity(
            uid = uid,
            barcode = barcode,
            name = name,
            calories = calories,
            loggedAt = Instant.now().toEpochMilli(),
            pendingSync = true
        )
        mealDao.insertMeal(meal)
    }

    fun observeMeals(): Flow<List<MealEntity>> {
        val uid = currentUid() ?: return emptyFlow()
        return mealDao.getMealsForUser(uid)
    }

    // placeholder for future cloud sync
    suspend fun getPendingMeals(): List<MealEntity> {
        val uid = currentUid() ?: return emptyList()
        return mealDao.getPendingMeals(uid)
    }

    suspend fun markSynced(ids: List<Long>) {
        mealDao.markSynced(ids)
    }
}

package com.marcomarais.welltrack.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MealEntity)

    @Query("SELECT * FROM meals WHERE uid = :uid ORDER BY loggedAt DESC")
    fun getMealsForUser(uid: String): Flow<List<MealEntity>>

    @Query("SELECT * FROM meals WHERE pendingSync = 1 AND uid = :uid")
    suspend fun getPendingMeals(uid: String): List<MealEntity>

    @Query("UPDATE meals SET pendingSync = 0 WHERE id IN (:ids)")
    suspend fun markSynced(ids: List<Long>)
}

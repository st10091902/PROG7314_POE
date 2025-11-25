package com.marcomarais.welltrack.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class MealEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val uid: String,          // Firebase user ID
    val barcode: String,
    val name: String,
    val calories: Int,
    val loggedAt: Long,       // epoch millis
    val pendingSync: Boolean  // true if not yet synced to cloud
)

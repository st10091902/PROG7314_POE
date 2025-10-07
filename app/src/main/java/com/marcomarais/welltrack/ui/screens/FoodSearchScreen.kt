package com.marcomarais.welltrack.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.marcomarais.welltrack.data.MealRepository
import com.marcomarais.welltrack.data.remote.ApiClient
import kotlinx.coroutines.launch

@Composable
fun FoodSearchScreen() {
    val repo = remember {
        MealRepository(ApiClient.api, FirebaseFirestore.getInstance(), FirebaseAuth.getInstance())
    }
    val scope = rememberCoroutineScope()

    var barcode by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(Modifier.padding(16.dp)) {
        Text("Food Search", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = barcode, onValueChange = { barcode = it }, label = { Text("Barcode") })
        Spacer(Modifier.height(8.dp))
        Row {
            Button(onClick = {
                scope.launch {
                    error = null; result = null
                    try {
                        val food = repo.fetchFood(barcode)
                        result = "${food.name} • ${food.calories} kcal"
                    } catch (t: Throwable) { error = t.message }
                }
            }) { Text("Lookup") }
            Spacer(Modifier.width(12.dp))
            Button(onClick = {
                scope.launch {
                    error = null
                    try {
                        val food = repo.fetchFood(barcode)
                        repo.logMeal(food, 1)
                        result = "Logged: ${food.name}"
                    } catch (t: Throwable) { error = t.message }
                }
            }) { Text("Log Meal") }
        }
        Spacer(Modifier.height(12.dp))
        if (result != null) Text(result!!)
        if (error != null) Text("Error: $error", color = MaterialTheme.colorScheme.error)
    }
}

package com.marcomarais.welltrack.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.marcomarais.welltrack.R
import com.marcomarais.welltrack.data.MealLocalRepository
import com.marcomarais.welltrack.data.MealRepository
import com.marcomarais.welltrack.data.local.WellTrackDatabase
import com.marcomarais.welltrack.data.remote.ApiClient
import com.marcomarais.welltrack.data.remote.FoodDto
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FoodSearchViewModel(
    private val repo: MealLocalRepository
) : ViewModel() {

    val meals = repo.observeMeals()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )

    fun logMealFromDto(dto: FoodDto) {
        viewModelScope.launch {
            repo.logMealLocally(
                barcode = dto.barcode,
                name = dto.name,
                calories = dto.calories
            )
        }
    }

    companion object {
        fun provideFactory(repo: MealLocalRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return FoodSearchViewModel(repo) as T
                }
            }
    }
}

@Composable
fun FoodSearchScreen() {
    val context = LocalContext.current
    val resources = context.resources

    // Local Room DB + repo
    val db = remember { WellTrackDatabase.getInstance(context) }
    val localRepo = remember { MealLocalRepository(db.mealDao()) }

    val vm: FoodSearchViewModel = viewModel(
        factory = FoodSearchViewModel.provideFactory(localRepo)
    )

    val meals by vm.meals.collectAsState()

    // Online repo only for fetching from API
    val onlineRepo = remember {
        MealRepository(
            ApiClient.api,
            FirebaseFirestore.getInstance(),
            FirebaseAuth.getInstance()
        )
    }

    val scope = rememberCoroutineScope()

    var barcode by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(Modifier.padding(16.dp)) {
        Text(
            text = stringResource(R.string.food_search_title),
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = barcode,
            onValueChange = { barcode = it },
            label = { Text(stringResource(R.string.barcode_label)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Row {
            // LOOKUP button
            Button(
                onClick = {
                    scope.launch {
                        error = null
                        result = null
                        try {
                            val food = onlineRepo.fetchFood(barcode)
                            // use context.getString INSIDE coroutine
                            result = resources.getString(
                                R.string.food_lookup_result,
                                food.name,
                                food.calories
                            )
                        } catch (t: Throwable) {
                            error = t.message
                        }
                    }
                }
            ) {
                Text(stringResource(R.string.food_lookup_button))
            }

            Spacer(Modifier.width(12.dp))

            // LOG MEAL button
            Button(
                onClick = {
                    scope.launch {
                        error = null
                        result = null
                        try {
                            val food = onlineRepo.fetchFood(barcode)
                            vm.logMealFromDto(food)   // store offline in Room
                            result = resources.getString(
                                R.string.food_logged_result,
                                food.name
                            )
                        } catch (t: Throwable) {
                            error = t.message
                        }
                    }
                }
            ) {
                Text(stringResource(R.string.food_log_meal_button))
            }
        }

        Spacer(Modifier.height(12.dp))

        result?.let {
            Text(it)
        }

        error?.let {
            Text(
                text = resources.getString(R.string.error_prefix, it),
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.recent_meals_title),
            style = MaterialTheme.typography.titleMedium
        )

        meals.forEach { meal ->
            Text(
                text = resources.getString(
                    R.string.recent_meal_item,
                    meal.name,
                    meal.calories
                )
            )
        }
    }
}

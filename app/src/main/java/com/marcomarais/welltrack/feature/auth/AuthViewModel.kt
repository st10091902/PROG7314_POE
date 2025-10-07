package com.marcomarais.welltrack.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _user = MutableStateFlow(auth.currentUser)
    val user = _user.asStateFlow()

    fun signInWithCredential(credential: AuthCredential, onError: (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                auth.signInWithCredential(credential).await()
                _user.value = auth.currentUser
            } catch (t: Throwable) {
                onError(t)
            }
        }
    }

    fun signOut() {
        auth.signOut()
        _user.value = null
    }
}

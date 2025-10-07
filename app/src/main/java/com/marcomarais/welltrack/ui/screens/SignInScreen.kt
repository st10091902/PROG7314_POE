package com.marcomarais.welltrack.ui.screens

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.marcomarais.welltrack.feature.auth.AuthViewModel

@Composable
fun SignInScreen(onSignedIn: () -> Unit, vm: AuthViewModel = viewModel()) {
    val user by vm.user.collectAsState()
    val context = LocalContext.current
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(user) { if (user != null) onSignedIn() }

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(com.marcomarais.welltrack.R.string.default_web_client_id))
        .requestEmail()
        .build()
    val client = GoogleSignIn.getClient(context, gso)

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { res ->
        if (res.resultCode == Activity.RESULT_OK) {
            runCatching {
                val account = GoogleSignIn.getSignedInAccountFromIntent(res.data).result
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                vm.signInWithCredential(credential) { t -> error = t.message }
            }.onFailure { error = it.message }
        } else {
            error = "Sign-in cancelled"
        }
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("WellTrack", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(24.dp))
            Button(onClick = { launcher.launch(client.signInIntent) }) {
                Text("Sign in with Google")
            }
            if (error != null) {
                Spacer(Modifier.height(8.dp))
                Text("Error: $error", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

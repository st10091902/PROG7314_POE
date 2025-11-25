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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.marcomarais.welltrack.R
import com.marcomarais.welltrack.feature.auth.AuthViewModel
import com.marcomarais.welltrack.feature.auth.BiometricHelper

@Composable
fun SignInScreen(
    onSignedIn: () -> Unit,
    vm: AuthViewModel = viewModel()
) {
    val user by vm.user.collectAsState()
    val context = LocalContext.current
    var error by remember { mutableStateOf<String?>(null) }

    // Biometric setup
    val activity = context as FragmentActivity
    val biometrics = remember { BiometricHelper(activity) }

    LaunchedEffect(user) {
        if (user != null) onSignedIn()
    }

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
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
            error = context.getString(R.string.sign_in_cancelled)
        }
    }

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.welltrack_title),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(24.dp))

            // GOOGLE SIGN-IN
            Button(onClick = { launcher.launch(client.signInIntent) }) {
                Text(text = stringResource(R.string.sign_in_with_google))
            }

            Spacer(Modifier.height(16.dp))

            // BIOMETRIC SIGN-IN
            Button(
                onClick = {
                    if (biometrics.canAuthenticate()) {
                        biometrics.startAuth(
                            activity,
                            onSuccess = {
                                vm.signInWithBiometric { failMsg ->
                                    if (failMsg != null) {
                                        error = failMsg.message
                                    }
                                }
                            },
                            onFail = { failMsg ->
                                error = failMsg
                            }
                        )
                    } else {
                        error = context.getString(R.string.biometric_not_available)
                    }
                }
            ) {
                Text(text = stringResource(R.string.sign_in_with_biometrics))
            }

            if (error != null) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "${stringResource(R.string.error_prefix)} $error",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

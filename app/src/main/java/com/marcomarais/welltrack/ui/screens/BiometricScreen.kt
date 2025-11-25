package com.marcomarais.welltrack.ui.screens

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

@Composable
fun BiometricScreen(
    onAuthSuccess: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as FragmentActivity

    var error by remember { mutableStateOf<String?>(null) }

    // What kinds of biometric auth we allow
    val authenticators = BiometricManager.Authenticators.BIOMETRIC_STRONG or
            BiometricManager.Authenticators.DEVICE_CREDENTIAL

    val biometrics = BiometricManager.from(context)
    val canAuth = biometrics.canAuthenticate(authenticators)

    // Prompt info
    val promptInfo = remember {
        BiometricPrompt.PromptInfo.Builder()
            .setTitle("Unlock WellTrack")
            .setSubtitle("Use fingerprint or device credentials")
            .setAllowedAuthenticators(authenticators)
            .build()
    }

    // BiometricPrompt instance
    val biometricPrompt = remember {
        BiometricPrompt(
            activity,
            ContextCompat.getMainExecutor(context),
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onAuthSuccess()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    error = errString.toString()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    error = "Authentication failed. Try again."
                }
            }
        )
    }

    // Auto-launch biometrics when screen opens
    LaunchedEffect(Unit) {
        if (canAuth == BiometricManager.BIOMETRIC_SUCCESS) {
            biometricPrompt.authenticate(promptInfo)
        } else {
            // If device has *no* biometrics configured, let user in normally
            onAuthSuccess()
        }
    }

    // UI
    Box(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text("Biometric Authentication",
                style = MaterialTheme.typography.headlineSmall)

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    if (canAuth == BiometricManager.BIOMETRIC_SUCCESS) {
                        biometricPrompt.authenticate(promptInfo)
                    } else {
                        error = "Biometric authentication unavailable on this device."
                    }
                }
            ) {
                Text("Retry Biometrics")
            }

            if (error != null) {
                Spacer(Modifier.height(16.dp))
                Text(error!!, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

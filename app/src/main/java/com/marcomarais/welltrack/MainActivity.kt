package com.marcomarais.welltrack

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.marcomarais.welltrack.feature.auth.AuthViewModel
import com.marcomarais.welltrack.feature.auth.BiometricHelper
import com.marcomarais.welltrack.ui.WellTrackRoot
import com.marcomarais.welltrack.ui.theme.WellTrackTheme

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Ask for notification permission on Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, arrayOf(permission), 1001)
            }
        }

        setContent {
            WellTrackTheme {
                val authVm: AuthViewModel = viewModel()
                val userState by authVm.user.collectAsState()

                val context = LocalContext.current
                val activity = this@MainActivity
                val biometrics = remember { BiometricHelper(context) }
                var biometricAsked by remember { mutableStateOf(false) }

                // Only try biometrics once, and only if the user is already logged in.
                // Failures do NOT close the app – user simply stays in the normal flow.
                LaunchedEffect(userState) {
                    if (userState != null && !biometricAsked) {
                        biometricAsked = true
                        if (biometrics.canAuthenticate()) {
                            biometrics.startAuth(
                                activity = activity,
                                onSuccess = {
                                    // Nothing special – user just continues into the app
                                },
                                onFail = {
                                    // Optionally log or show a toast; we don’t close the app.
                                }
                            )
                        }
                    }
                }

                // Normal app nav (sign-in + home, etc.)
                WellTrackRoot(authViewModel = authVm)
            }
        }
    }
}

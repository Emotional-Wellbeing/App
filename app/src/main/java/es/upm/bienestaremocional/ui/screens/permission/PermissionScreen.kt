package es.upm.bienestaremocional.ui.screens.permission

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.READ_CALL_LOG
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.health.connect.client.HealthConnectClient
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.ui.component.AppBasicScreen
import es.upm.bienestaremocional.ui.screens.destinations.HomeScreenDestination
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import es.upm.bienestaremocional.utils.openForeignActivity

/**
 * Contains info about people involved in the app
 */

@Destination
@Composable
fun PermissionScreen(
    navigator: DestinationsNavigator,
    viewModel: PermissionViewModel = hiltViewModel()
) {
    val requestNotificationPermission: @Composable ((Boolean) -> Unit) -> Unit = { onResult ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            viewModel.RequestNotificationPermission(onResult = onResult)
    }
    PermissionScreen(
        navigator = navigator,
        requestNotificationPermission = requestNotificationPermission,
        onFinish = {
            viewModel.onFinish()
            navigator.navigate(HomeScreenDestination)
        }
    )
}

@Composable
private fun PermissionScreen(
    navigator: DestinationsNavigator,
    requestNotificationPermission: @Composable ((Boolean) -> Unit) -> Unit,
    onFinish: () -> Unit,
) {
    val context = LocalContext.current

    var showNotificationDialog by remember { mutableStateOf(false) }

    val onGrantedPermission = {
        Toast.makeText(
            context,
            R.string.permission_granted,
            Toast.LENGTH_LONG
        ).show()
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { result ->
            if (result)
                onGrantedPermission()
        }
    )

    if (showNotificationDialog) {
        val onResult: (Boolean) -> Unit = { result ->
            if (result)
                onGrantedPermission()
            showNotificationDialog = false
        }

        requestNotificationPermission(onResult)
    }

    AppBasicScreen(
        navigator = navigator,
        entrySelected = null,
        label = R.string.permission_screen_label
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {

            Text(
                text = stringResource(id = R.string.permission_intro),
                textAlign = TextAlign.Justify
            )
            TextButton(
                onClick = {
                    checkAndRequestPermission(
                        context,
                        READ_CALL_LOG,
                        launcher
                    )
                }
            ) {
                Text(text = stringResource(R.string.permission_log_call))
            }

            Text(
                text = stringResource(id = R.string.permission_location_description),
                textAlign = TextAlign.Justify
            )
            TextButton(
                onClick = {
                    checkAndRequestPermission(
                        context,
                        ACCESS_FINE_LOCATION,
                        launcher
                    )
                    checkAndRequestPermission(
                        context,
                        ACCESS_COARSE_LOCATION,
                        launcher
                    )
                }
            ) {
                Text(text = stringResource(R.string.permission_location))
            }

            Text(
                text = stringResource(id = R.string.permission_usage_description),
                textAlign = TextAlign.Justify
            )
            TextButton(
                onClick = {
                    openForeignActivity(
                        context = context,
                        action = Settings.ACTION_USAGE_ACCESS_SETTINGS
                    )
                }
            ) {
                Text(text = stringResource(id = R.string.permission_usage))
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Text(
                    text = stringResource(id = R.string.permission_notification_description),
                    textAlign = TextAlign.Justify
                )
                TextButton(
                    onClick = { showNotificationDialog = true }
                ) {
                    Text(text = stringResource(R.string.permission_notification))
                }
            }

            Text(
                text = stringResource(id = R.string.permission_health_connect_description),
                textAlign = TextAlign.Justify
            )
            TextButton(
                onClick = {
                    openForeignActivity(
                        context = context,
                        action = HealthConnectClient.ACTION_HEALTH_CONNECT_SETTINGS
                    )
                }
            ) {
                Text(text = stringResource(R.string.permission_health_connect))
            }

            Text(
                text = stringResource(id = R.string.permission_outro),
                textAlign = TextAlign.Justify
            )
            Button(onClick = onFinish) {
                Text(text = stringResource(id = R.string.continue_label))
            }
        }
    }
}


fun checkAndRequestPermission(
    context: Context,
    permission: String,
    launcher: ManagedActivityResultLauncher<String, Boolean>
) {
    val permissionCheckResult = ContextCompat.checkSelfPermission(context, permission)
    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
        Toast.makeText(
            context,
            R.string.permission_granted,
            Toast.LENGTH_LONG
        ).show()
    }
    else {
        launcher.launch(permission)
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun PermissionScreenPreview() {
    BienestarEmocionalTheme {
        PermissionScreen(
            navigator = EmptyDestinationsNavigator,
            requestNotificationPermission = {},
            onFinish = {}
        )
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun PermissionScreenPreviewDarkTheme() {
    BienestarEmocionalTheme(darkTheme = true) {
        PermissionScreen(
            navigator = EmptyDestinationsNavigator,
            requestNotificationPermission = {},
            onFinish = {}
        )
    }
}

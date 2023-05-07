package es.upm.bienestaremocional.app.ui.screens.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.app.ui.screens.destinations.HomeScreenDestination
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.responsive.computeWindowHeightSize
import es.upm.bienestaremocional.core.ui.responsive.computeWindowWidthSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Contains info about people involved in the app
 */

@Destination
@Composable
fun PermissionScreen(navigator: DestinationsNavigator,
                  viewModel: PermissionViewModel = hiltViewModel()
)
{
    PermissionScreen(navigator = navigator,
        widthSize = computeWindowWidthSize(),
        heightSize = computeWindowHeightSize()
    )
}

@Composable
private fun PermissionScreen(navigator: DestinationsNavigator,
                          widthSize: WindowWidthSizeClass,
                          heightSize: WindowHeightSizeClass
)
{
    AppBasicScreen(navigator = navigator,
        entrySelected = BottomBarDestination.SettingsScreen,
        label = R.string.permission_screen_label)
    {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val context = LocalContext.current

                val launcher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) {
                }
                Button(
                    onClick = {
                        checkAndRequestPermission(
                            context,
                            Manifest.permission.READ_CALL_LOG,
                            launcher
                        )
                    }
                ) {
                    Text(text = "Permisos de listado de llamadas")
                }

                Button(
                    onClick = {
                        checkAndRequestPermission(
                            context,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            launcher
                        )
                    }
                ) {
                    Text(text = "Permisos de ubicación")
                }

                Button(
                    onClick = {
                        checkAndRequestPermission(context, Manifest.permission.INTERNET, launcher)
                    }
                ) {
                    Text(text = "Permisos de internet")
                }

                Divider(modifier = Modifier.padding(top = 150.dp, bottom = 16.dp))

                ExtendedFloatingActionButton(onClick = { navigator.navigate(HomeScreenDestination) }) {
                    Text(text = "HOME")
                }

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
            "Permiso concedido. Puede deshabilitar permisos en el menú de configuración de Android",
            Toast.LENGTH_LONG
        ).show()
    } else {
        launcher.launch(permission)
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun PermissionScreenPreviewCompactScreen()
{

    BienestarEmocionalTheme {
        PermissionScreen(navigator = EmptyDestinationsNavigator,
            widthSize = WindowWidthSizeClass.Compact,
            heightSize = WindowHeightSizeClass.Compact
        )
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun PermissionScreenPreviewDarkThemeCompactScreen()
{

    BienestarEmocionalTheme(darkTheme = true) {
        PermissionScreen(navigator = EmptyDestinationsNavigator,
            widthSize = WindowWidthSizeClass.Compact,
            heightSize = WindowHeightSizeClass.Compact
        )
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun PermissionScreenPreviewMediumScreen()
{

    BienestarEmocionalTheme {
        PermissionScreen(navigator = EmptyDestinationsNavigator,
            widthSize = WindowWidthSizeClass.Medium,
            heightSize = WindowHeightSizeClass.Medium
        )
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun PermissionScreenPreviewDarkThemeMediumScreen()
{

    BienestarEmocionalTheme(darkTheme = true) {
        PermissionScreen(navigator = EmptyDestinationsNavigator,
            widthSize = WindowWidthSizeClass.Medium,
            heightSize = WindowHeightSizeClass.Medium
        )
    }
}
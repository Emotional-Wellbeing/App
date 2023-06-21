package es.upm.bienestaremocional.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.healthconnect.sources.Sleep
import es.upm.bienestaremocional.data.healthconnect.types.SleepSessionData
import es.upm.bienestaremocional.ui.component.ViewModelData
import javax.inject.Inject

@HiltViewModel
class SleepSessionViewModel @Inject constructor(
    private val sleep: Sleep
) : HealthConnectViewModel<SleepSessionData>() {
    @Composable
    override fun getViewModelData(): ViewModelData<SleepSessionData> {
        val data by elements
        val onPermissionsResult = { readData(sleep) }

        val permissionsLauncher =
            rememberLauncherForActivityResult(permissionLauncher) { onPermissionsResult() }

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = sleep.readPermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = { values -> permissionsLauncher.launch(values) },
        )
    }
}


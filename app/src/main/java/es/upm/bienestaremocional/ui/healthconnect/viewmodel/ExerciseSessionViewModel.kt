package es.upm.bienestaremocional.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.ExerciseSessionRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.healthconnect.sources.ExerciseSession
import es.upm.bienestaremocional.ui.component.ViewModelData
import javax.inject.Inject

@HiltViewModel
class ExerciseSessionViewModel @Inject constructor(
    private val exerciseSession: ExerciseSession
): HealthConnectViewModel<ExerciseSessionRecord>()
{
    @Composable
    override fun getViewModelData(): ViewModelData<ExerciseSessionRecord>
    {
        val data by elements
        val onPermissionsResult = {readData(exerciseSession)}

        //launcher is a special case
        val permissionsLauncher =
            rememberLauncherForActivityResult(permissionLauncher) { onPermissionsResult() }

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = exerciseSession.readPermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = { values -> permissionsLauncher.launch(values)},
        )
    }
}

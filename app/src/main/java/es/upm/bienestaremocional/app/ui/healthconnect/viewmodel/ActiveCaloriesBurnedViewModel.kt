package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.ActiveCaloriesBurnedRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.healthconnect.sources.ActiveCaloriesBurned
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData
import javax.inject.Inject


@HiltViewModel
class ActiveCaloriesBurnedViewModel @Inject constructor(
    private val activeCaloriesBurned: ActiveCaloriesBurned
): HealthConnectViewModel<ActiveCaloriesBurnedRecord>()
{
    private fun writeAndReadDummyData()
    {
        writeData(activeCaloriesBurned,ActiveCaloriesBurned.generateDummyData())
        readData(activeCaloriesBurned)
    }

    @Composable
    override fun getViewModelData(): ViewModelData<ActiveCaloriesBurnedRecord>
    {
        val data by elements
        val onPermissionsResult = {readData(activeCaloriesBurned)}

        val launcher = rememberLauncherForActivityResult(contract = permissionLauncher,
            onResult = {onPermissionsResult()})

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = activeCaloriesBurned.readPermissions +
                    activeCaloriesBurned.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = {values -> launcher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}
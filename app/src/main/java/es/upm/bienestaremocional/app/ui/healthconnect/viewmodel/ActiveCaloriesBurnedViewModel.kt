package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.ActiveCaloriesBurnedRecord
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.healthconnect.sources.ActiveCaloriesBurned
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData

class ActiveCaloriesBurnedViewModel(private val activeCaloriesBurned: ActiveCaloriesBurned) :
    HealthConnectViewModel<ActiveCaloriesBurnedRecord>()
{
    companion object
    {
        /**
         * Factory to instance [ActiveCaloriesBurnedViewModel]
         */
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ActiveCaloriesBurnedViewModel(
                    ActiveCaloriesBurned(
                        healthConnectClient = MainApplication.healthConnectClient,
                        healthConnectManager = MainApplication.healthConnectManager
                    )
                )
            }
        }
    }

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
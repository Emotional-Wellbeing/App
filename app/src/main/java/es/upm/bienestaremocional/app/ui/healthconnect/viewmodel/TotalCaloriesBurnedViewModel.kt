package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.healthconnect.sources.TotalCaloriesBurned
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData

class TotalCaloriesBurnedViewModel(private val totalCaloriesBurned: TotalCaloriesBurned) :
    HealthConnectViewModel<TotalCaloriesBurnedRecord>()
{
    companion object
    {
        /**
         * Factory to instance [TotalCaloriesBurnedViewModel]
         */
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                TotalCaloriesBurnedViewModel(
                    TotalCaloriesBurned(
                        healthConnectClient = MainApplication.healthConnectClient,
                        healthConnectManager = MainApplication.healthConnectManager
                    )
                )
            }
        }
    }

    private fun writeAndReadDummyData()
    {
        writeData(totalCaloriesBurned, TotalCaloriesBurned.generateDummyData())
        readData(totalCaloriesBurned)
    }

    @Composable
    override fun getViewModelData(): ViewModelData<TotalCaloriesBurnedRecord>
    {
        val data by elements
        val onPermissionsResult = {readData(totalCaloriesBurned)}

        val launcher = rememberLauncherForActivityResult(contract = permissionLauncher,
            onResult = {onPermissionsResult()})

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = totalCaloriesBurned.readPermissions + totalCaloriesBurned.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = {values -> launcher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}
package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.DistanceRecord
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.healthconnect.sources.Distance
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData

class DistanceViewModel(private val distance: Distance) :
    HealthConnectViewModel<DistanceRecord>()
{
    companion object
    {
        /**
         * Factory class to instance [DistanceViewModel]
         */
        val Factory : ViewModelProvider.Factory = viewModelFactory{
            initializer {
                DistanceViewModel(
                    Distance(
                        healthConnectClient = MainApplication.healthConnectClient,
                        healthConnectManager = MainApplication.healthConnectManager
                    )
                )
            }
        }
    }

    private fun writeAndReadDummyData()
    {
        writeData(distance, Distance.generateDummyData())
        readData(distance)
    }

    @Composable
    override fun getViewModelData(): ViewModelData<DistanceRecord>
    {
        val data by elements
        val onPermissionsResult = {readData(distance)}

        //launcher is a special case
        val permissionsLauncher =
            rememberLauncherForActivityResult(permissionLauncher) { onPermissionsResult() }

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = distance.readPermissions + distance.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = { values -> permissionsLauncher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}

package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.records.ElevationGainedRecord
import androidx.health.connect.client.records.Record
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.healthconnect.sources.ElevationGained
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData

class ElevationGainedViewModel(private val elevationGained: ElevationGained) :
    HealthConnectViewModel()
{
    companion object
    {
        /**
         * Factory to instance [ElevationGainedViewModel]
         */
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ElevationGainedViewModel(
                    ElevationGained(
                        healthConnectClient = MainApplication.healthConnectClient,
                        healthConnectManager = MainApplication.healthConnectManager
                    )
                )
            }
        }
    }
    private var elevationData : MutableState<List<ElevationGainedRecord>> = mutableStateOf(listOf())

    /**
     * Read data calling [HealthConnectViewModel.readData]
     */
    private fun readData()
    {
        @Suppress("UNCHECKED_CAST")
        super.readData(healthConnectSource = elevationGained,
            data = elevationData as MutableState<List<Record>>)
    }

    private fun writeData(data: List<Record>)
    {
        super.writeData(healthConnectSource = elevationGained, data = data)
    }

    private fun writeData()
    {
        writeData(ElevationGained.generateDummyData())
    }

    private fun writeAndReadDummyData()
    {
        writeData()
        readData()
    }

    @Composable
    override fun getViewModelData(): ViewModelData
    {
        val data by elevationData
        val onPermissionsResult = {readData()}

        val launcher = rememberLauncherForActivityResult(contract = permissionLauncher,
            onResult = {onPermissionsResult()})

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = elevationGained.readPermissions + elevationGained.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = {values -> launcher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}
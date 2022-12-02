package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.records.BloodGlucoseRecord
import androidx.health.connect.client.records.Record
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.healthconnect.sources.BloodGlucose
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData

class BloodGlucoseViewModel(private val bloodGlucose: BloodGlucose) : HealthConnectViewModel()
{
    companion object
    {
        /**
         * Factory to instance [BloodGlucoseViewModel]
         */
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                BloodGlucoseViewModel(
                    BloodGlucose(
                        healthConnectClient = MainApplication.healthConnectClient,
                        healthConnectManager = MainApplication.healthConnectManager
                    )
                )
            }
        }
    }
    private var bgData : MutableState<List<BloodGlucoseRecord>> = mutableStateOf(listOf())

    /**
     * Read data calling [HealthConnectViewModel.readData]
     */
    private fun readData()
    {
        @Suppress("UNCHECKED_CAST")
        super.readData(healthConnectSource = bloodGlucose,
            data = bgData as MutableState<List<Record>>)
    }

    private fun writeData(data: List<Record>)
    {
        super.writeData(healthConnectSource = bloodGlucose, data = data)
    }

    private fun writeData()
    {
        writeData(BloodGlucose.generateDummyData())
    }

    private fun writeAndReadDummyData()
    {
        writeData()
        readData()
    }

    @Composable
    override fun getViewModelData(): ViewModelData
    {
        val data by bgData
        val onPermissionsResult = {readData()}

        val launcher = rememberLauncherForActivityResult(contract = permissionLauncher,
            onResult = {onPermissionsResult()})

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = bloodGlucose.readPermissions + bloodGlucose.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = {values -> launcher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}
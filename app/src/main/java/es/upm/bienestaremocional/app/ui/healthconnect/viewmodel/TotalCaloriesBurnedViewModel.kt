package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.healthconnect.sources.TotalCaloriesBurned
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData

class TotalCaloriesBurnedViewModel(private val totalCaloriesBurned: TotalCaloriesBurned) :
    HealthConnectViewModel()
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
    private var tcbData : MutableState<List<TotalCaloriesBurnedRecord>> = mutableStateOf(listOf())

    /**
     * Read data calling [HealthConnectViewModel.readData]
     */
    private fun readData()
    {
        @Suppress("UNCHECKED_CAST")
        super.readData(healthConnectSource = totalCaloriesBurned,
            data = tcbData as MutableState<List<Record>>)
    }

    private fun writeData(data: List<Record>)
    {
        super.writeData(healthConnectSource = totalCaloriesBurned, data = data)
    }

    private fun writeData()
    {
        writeData(TotalCaloriesBurned.generateDummyData())
    }

    private fun writeAndReadDummyData()
    {
        writeData()
        readData()
    }

    @Composable
    override fun getViewModelData(): ViewModelData
    {
        val data by tcbData
        val onPermissionsResult = {readData()}

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
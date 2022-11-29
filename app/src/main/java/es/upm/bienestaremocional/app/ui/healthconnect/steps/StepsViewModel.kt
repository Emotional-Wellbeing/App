package es.upm.bienestaremocional.app.ui.healthconnect.steps

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.records.StepsRecord
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.healthconnect.sources.Steps
import es.upm.bienestaremocional.app.data.healthconnect.types.SleepSessionData
import es.upm.bienestaremocional.app.ui.healthconnect.sleep.SleepSessionViewModel
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData
import kotlinx.coroutines.launch

class StepsViewModel(private val steps: Steps) : HealthConnectViewModel()
{
    companion object
    {
        /**
         * Factory class to instance [SleepSessionViewModel]
         */
        val Factory : ViewModelProvider.Factory = viewModelFactory{
            initializer {
                StepsViewModel(
                    Steps(
                        healthConnectClient = MainApplication.healthConnectClient,
                        healthConnectManager = MainApplication.healthConnectManager
                    )
                )
            }
        }
    }
    //data of viewmodel
    private var stepsData: MutableState<List<StepsRecord>> = mutableStateOf(listOf())

    /**
     * Implements [HealthConnectViewModel.readData] with [SleepSessionData] data
     */
    private fun readStepsData()
    {
        @Suppress("UNCHECKED_CAST")

        //This cast can sucess because SleepSessionData implements Record
        super.readData(healthConnectSource = steps, data = stepsData as MutableState<List<Record>>)
    }

    /**
     * Demo function used to write and read the data to show it
     */
    private fun writeAndReadDummyData()
    {
        writeStepsData()
        readStepsData()
    }

    /**
     * Generate dummy data
     */
    private fun writeStepsData()
    {
        writeStepsData(Steps.generateDummyData())
    }

    /**
     * Write data using [Steps.writeSource]
     */
    private fun writeStepsData(data: List<Record>)
    {
        viewModelScope.launch {
            if (steps.writePermissionsCheck())
                steps.writeSource(data)
        }
    }

    @Composable
    override fun getViewModelData(): ViewModelData
    {
        val data by stepsData
        val onPermissionsResult = {readStepsData()}

        //launcher is a special case
        val permissionsLauncher =
            rememberLauncherForActivityResult(permissionLauncher) { onPermissionsResult() }

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = steps.readPermissions + steps.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = { values -> permissionsLauncher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}
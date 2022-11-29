
package es.upm.bienestaremocional.app.ui.healthconnect.sleep

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.*
import androidx.health.connect.client.records.Record
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.healthconnect.sources.Sleep
import es.upm.bienestaremocional.app.data.healthconnect.types.SleepSessionData
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData
import kotlinx.coroutines.launch

class SleepSessionViewModel(val sleep: Sleep) :
    HealthConnectViewModel()
{
    companion object
    {
        /**
         * Factory class to instance [SleepSessionViewModel]
         */
        val Factory : ViewModelProvider.Factory = viewModelFactory{
            initializer {
                SleepSessionViewModel(
                    Sleep(
                        healthConnectClient = MainApplication.healthConnectClient,
                        healthConnectManager = MainApplication.healthConnectManager
                    )
                )
            }
        }
    }
    //data of viewmodel
    var sleepData: MutableState<List<SleepSessionData>> = mutableStateOf(listOf())

    /**
     * Implements [HealthConnectViewModel.readData] with [SleepSessionData] data
     */
    fun readSleepData()
    {
        @Suppress("UNCHECKED_CAST")

        //This cast can sucess because SleepSessionData implements Record
        super.readData(healthConnectSource = sleep,
            data = sleepData as MutableState<List<Record>>)
    }

    /**
     * Demo function used to write and read the data to show it
     */
    private fun writeAndReadDummyData()
    {
        writeSleepData()
        readSleepData()
    }

    /**
     * Generate dummy data
     */
    private fun writeSleepData()
    {
        writeSleepData(Sleep.generateDummyData())
    }

    /**
     * Write data using [Sleep.writeSource]
     */
    private fun writeSleepData(data: List<Record>)
    {
        viewModelScope.launch {
            if (sleep.writePermissionsCheck())
                sleep.writeSource(data)
        }
    }

    @Composable
    override fun getViewModelData(): ViewModelData
    {
        val data by sleepData
        val onPermissionsResult = {readSleepData()}

        //launcher is a special case
        val permissionsLauncher =
            rememberLauncherForActivityResult(permissionLauncher) { onPermissionsResult() }

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = sleep.readPermissions + sleep.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = { values -> permissionsLauncher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}


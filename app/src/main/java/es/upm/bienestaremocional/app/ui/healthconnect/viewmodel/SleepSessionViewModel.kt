
package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.healthconnect.sources.Sleep
import es.upm.bienestaremocional.app.data.healthconnect.types.SleepSessionData
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData

class SleepSessionViewModel(private val sleep: Sleep) :
    HealthConnectViewModel<SleepSessionData>()
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

    private fun writeAndReadDummyData()
    {
        val data = Sleep.generateDummyData()
        super.writeData(sleep, data.map { it.toSleepSessionRecord() })
        data.forEach {
            super.writeData(sleep, it.stages)
        }
        readData(sleep)
    }

    @Composable
    override fun getViewModelData(): ViewModelData<SleepSessionData>
    {
        val data by elements
        val onPermissionsResult = {readData(sleep)}

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


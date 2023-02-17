package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.BodyTemperatureRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.healthconnect.sources.BodyTemperature
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData
import javax.inject.Inject

@HiltViewModel
class BodyTemperatureViewModel @Inject constructor(
    private val bodyTemperature: BodyTemperature
): HealthConnectViewModel<BodyTemperatureRecord>()
{
    private fun writeAndReadDummyData()
    {
        writeData(bodyTemperature,BodyTemperature.generateDummyData())
        readData(bodyTemperature)
    }

    @Composable
    override fun getViewModelData(): ViewModelData<BodyTemperatureRecord>
    {
        val data by elements
        val onPermissionsResult = {readData(bodyTemperature)}

        val launcher = rememberLauncherForActivityResult(contract = permissionLauncher,
            onResult = {onPermissionsResult()})

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = bodyTemperature.readPermissions + bodyTemperature.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = {values -> launcher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}
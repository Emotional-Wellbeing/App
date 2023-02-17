package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.OxygenSaturationRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.healthconnect.sources.OxygenSaturation
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData
import javax.inject.Inject

@HiltViewModel
class OxygenSaturationViewModel @Inject constructor(
    private val oxygenSaturation: OxygenSaturation
): HealthConnectViewModel<OxygenSaturationRecord>()
{
    private fun writeAndReadDummyData()
    {
        writeData(oxygenSaturation,OxygenSaturation.generateDummyData())
        readData(oxygenSaturation)
    }

    @Composable
    override fun getViewModelData(): ViewModelData<OxygenSaturationRecord>
    {
        val data by elements
        val onPermissionsResult = {readData(oxygenSaturation)}

        val launcher = rememberLauncherForActivityResult(contract = permissionLauncher,
            onResult = {onPermissionsResult()})

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = oxygenSaturation.readPermissions + oxygenSaturation.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = {values -> launcher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}
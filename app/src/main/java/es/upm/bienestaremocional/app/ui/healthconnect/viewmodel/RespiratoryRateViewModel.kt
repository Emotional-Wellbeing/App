package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.RespiratoryRateRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.healthconnect.sources.RespiratoryRate
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData
import javax.inject.Inject

@HiltViewModel
class RespiratoryRateViewModel @Inject constructor(
    private val respiratoryRate: RespiratoryRate
): HealthConnectViewModel<RespiratoryRateRecord>()
{
    private fun writeAndReadDummyData()
    {
        writeData(respiratoryRate,RespiratoryRate.generateDummyData())
        readData(respiratoryRate)
    }

    @Composable
    override fun getViewModelData(): ViewModelData<RespiratoryRateRecord>
    {
        val data by elements
        val onPermissionsResult = {readData(respiratoryRate)}

        val launcher = rememberLauncherForActivityResult(contract = permissionLauncher,
            onResult = {onPermissionsResult()})

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = respiratoryRate.readPermissions + respiratoryRate.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = {values -> launcher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}
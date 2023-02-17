package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.RestingHeartRateRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.healthconnect.sources.RestingHeartRate
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData
import javax.inject.Inject

@HiltViewModel
class RestingHeartRateViewModel @Inject constructor(
    private val restingHeartRate: RestingHeartRate
): HealthConnectViewModel<RestingHeartRateRecord>()
{
    private fun writeAndReadDummyData()
    {
        writeData(restingHeartRate,RestingHeartRate.generateDummyData())
        readData(restingHeartRate)
    }

    @Composable
    override fun getViewModelData(): ViewModelData<RestingHeartRateRecord>
    {
        val data by elements
        val onPermissionsResult = {readData(restingHeartRate)}

        val launcher = rememberLauncherForActivityResult(contract = permissionLauncher,
            onResult = {onPermissionsResult()})

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = restingHeartRate.readPermissions + restingHeartRate.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = {values -> launcher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}
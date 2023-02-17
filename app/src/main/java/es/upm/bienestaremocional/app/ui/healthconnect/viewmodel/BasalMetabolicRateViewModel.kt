package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.BasalMetabolicRateRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.healthconnect.sources.BasalMetabolicRate
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData
import javax.inject.Inject

@HiltViewModel
class BasalMetabolicRateViewModel @Inject constructor(
    private val basalMetabolicRate: BasalMetabolicRate
): HealthConnectViewModel<BasalMetabolicRateRecord>()
{

    private fun writeAndReadDummyData()
    {
        writeData(basalMetabolicRate,BasalMetabolicRate.generateDummyData())
        readData(basalMetabolicRate)
    }

    @Composable
    override fun getViewModelData(): ViewModelData<BasalMetabolicRateRecord>
    {
        val data by elements
        val onPermissionsResult = {readData(basalMetabolicRate)}

        val launcher = rememberLauncherForActivityResult(contract = permissionLauncher,
            onResult = {onPermissionsResult()})

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = basalMetabolicRate.readPermissions + basalMetabolicRate.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = {values -> launcher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}
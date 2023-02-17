package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.BloodGlucoseRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.healthconnect.sources.BloodGlucose
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData
import javax.inject.Inject

@HiltViewModel
class BloodGlucoseViewModel @Inject constructor(
    private val bloodGlucose: BloodGlucose
): HealthConnectViewModel<BloodGlucoseRecord>()
{
    private fun writeAndReadDummyData()
    {
        writeData(bloodGlucose,BloodGlucose.generateDummyData())
        readData(bloodGlucose)
    }

    @Composable
    override fun getViewModelData(): ViewModelData<BloodGlucoseRecord>
    {
        val data by elements
        val onPermissionsResult = {readData(bloodGlucose)}

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
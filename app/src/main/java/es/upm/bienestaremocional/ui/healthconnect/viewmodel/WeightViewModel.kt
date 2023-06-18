package es.upm.bienestaremocional.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.WeightRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.healthconnect.sources.Weight
import es.upm.bienestaremocional.ui.component.ViewModelData
import javax.inject.Inject

@HiltViewModel
class WeightViewModel @Inject constructor(
    private val weight: Weight
) : HealthConnectViewModel<WeightRecord>() {

    private fun writeAndReadDummyData() {
        writeData(weight, Weight.generateDummyData())
        readData(weight)
    }

    @Composable
    override fun getViewModelData(): ViewModelData<WeightRecord> {
        val data by elements
        val onPermissionsResult = { readData(weight) }

        //launcher is a special case
        val permissionsLauncher =
            rememberLauncherForActivityResult(permissionLauncher) { onPermissionsResult() }

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = weight.readPermissions + weight.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = { values -> permissionsLauncher.launch(values) },
            onWrite = this::writeAndReadDummyData
        )
    }
}

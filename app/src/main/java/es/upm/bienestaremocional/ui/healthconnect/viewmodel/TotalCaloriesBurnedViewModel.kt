package es.upm.bienestaremocional.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.healthconnect.sources.TotalCaloriesBurned
import es.upm.bienestaremocional.ui.component.ViewModelData
import javax.inject.Inject

@HiltViewModel
class TotalCaloriesBurnedViewModel @Inject constructor(
    private val totalCaloriesBurned: TotalCaloriesBurned
) : HealthConnectViewModel<TotalCaloriesBurnedRecord>() {
    private fun writeAndReadDummyData() {
        writeData(totalCaloriesBurned, TotalCaloriesBurned.generateDummyData())
        readData(totalCaloriesBurned)
    }
    @Composable
    override fun getViewModelData(): ViewModelData<TotalCaloriesBurnedRecord> {
        val data by elements
        val onPermissionsResult = { readData(totalCaloriesBurned) }

        val launcher = rememberLauncherForActivityResult(contract = permissionLauncher,
            onResult = { onPermissionsResult() })

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = totalCaloriesBurned.readPermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = { values -> launcher.launch(values) },
            onWrite = { writeAndReadDummyData() }
        )
    }
}
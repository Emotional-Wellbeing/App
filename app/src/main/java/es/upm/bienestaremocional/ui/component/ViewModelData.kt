package es.upm.bienestaremocional.ui.component

import androidx.health.connect.client.records.Record
import es.upm.bienestaremocional.ui.healthconnect.UiState

data class ViewModelData<T: Record>(
    val data: List<T>,
    val uiState: UiState,
    val permissions: Set<String>,
    val onPermissionsResult : () -> Unit,
    val onRequestPermissions : (Set<String>) -> Unit
)
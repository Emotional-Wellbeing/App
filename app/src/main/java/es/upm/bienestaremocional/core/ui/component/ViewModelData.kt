package es.upm.bienestaremocional.core.ui.component

import androidx.health.connect.client.records.Record
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.UiState

data class ViewModelData<T: Record>(
    val data: List<T>,
    val uiState: UiState,
    val permissions: Set<String>,
    val onPermissionsResult : () -> Unit,
    val onRequestPermissions : (Set<String>) -> Unit,
    val onWrite : () -> Unit,
)
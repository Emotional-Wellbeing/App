package es.upm.bienestaremocional.core.extraction.healthconnect.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.health.connect.client.records.Record
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSourceInterface
import es.upm.bienestaremocional.core.ui.component.ViewModelData

/**
 * Defines common features shared by all viewmodels that interact with healthconnect
 */
interface HealthConnectViewModelInterface
{
    /**
     * Reads data from [healthConnectSource]
     * @param healthConnectSource: source to read
     * @param data: [List] of [MutableState] of [Record] that holds data read
     */
    fun readData(healthConnectSource: HealthConnectSourceInterface,
                 data: MutableState<List<Record>>)

    @Composable
    fun getViewModelData(): ViewModelData
}
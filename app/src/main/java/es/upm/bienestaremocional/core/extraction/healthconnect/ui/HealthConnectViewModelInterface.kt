package es.upm.bienestaremocional.core.extraction.healthconnect.ui

import androidx.compose.runtime.Composable
import androidx.health.connect.client.records.Record
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSourceInterface
import es.upm.bienestaremocional.core.ui.component.ViewModelData

/**
 * Defines common features shared by all viewmodels that interact with healthconnect
 */
interface HealthConnectViewModelInterface<T: Record>
{
    /**
     * Reads data from [healthConnectSource]
     * @param healthConnectSource: source to read
     */
    fun readData(healthConnectSource: HealthConnectSourceInterface<T>)

    /**
     * Write data into [healthConnectSource]
     * @param healthConnectSource: source to write
     * @param data: [List] of [Record] that holds data to write
     */
    fun writeData(healthConnectSource: HealthConnectSourceInterface<T>, data: List<Record>)

    @Composable
    fun getViewModelData(): ViewModelData<T>
}
package es.upm.bienestaremocional.core.extraction.healthconnect.data

interface HealthConnectSource
{
    suspend fun permissionsCheck(): Boolean
    suspend fun readSource(): List<HealthConnectDataClass>
}
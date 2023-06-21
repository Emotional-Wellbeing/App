package es.upm.bienestaremocional.domain.processing

import java.time.ZonedDateTime

data class PureChartRecord(
    val day: ZonedDateTime,
    val score: Float
)

data class NullableChartRecord(
    val day: ZonedDateTime,
    val score: Float?,
)

/* TODO implement simulated elements
data class SimulatedChartRecord(
    val day : ZonedDateTime,
    val score : Float,
    val simulated : Boolean = false
) */

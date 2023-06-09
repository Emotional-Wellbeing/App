package es.upm.bienestaremocional.data

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.questionnaire.Level

enum class Measure(
    val id: String,
    val mandatory : Boolean,
    val frequency: Frequency,
    @StringRes val measureRes: Int,
    val advices : Map<Level,List<Int>>?
) {
    Stress(
        id = "stress",
        mandatory = true,
        frequency = Frequency.DailyAndOneOff,
        measureRes = R.string.stress,
        advices = mapOf(
            Pair(Level.Low, listOf(R.string.low_stress_advice)),
            Pair(Level.Moderate, listOf(R.string.moderate_stress_advice)),
            Pair(Level.High, listOf(R.string.high_stress_advice)),
        )
    ),
    Depression(
        id = "depression",
        mandatory = false,
        frequency = Frequency.DailyAndOneOff,
        measureRes = R.string.depression,
        advices = mapOf(
            Pair(Level.Low, listOf(R.string.low_depression_advice)),
            Pair(Level.Moderate, listOf(R.string.moderate_depression_advice)),
            Pair(Level.High, listOf(R.string.high_depression_advice)),
        )
    ),
    Loneliness(
        id = "loneliness",
        mandatory = false,
        frequency = Frequency.DailyAndOneOff,
        measureRes = R.string.loneliness,
        advices = mapOf(
            Pair(Level.Low, listOf(R.string.low_loneliness_advice)),
            Pair(Level.Moderate, listOf(R.string.moderate_loneliness_advice)),
            Pair(Level.High, listOf(R.string.high_loneliness_advice)),
        )
    ),
    Suicide(
        id = "suicide",
        mandatory = true,
        frequency = Frequency.OnlyDaily,
        measureRes = R.string.suicide,
        advices = null
    ),
    Symptoms(
        id = "symptoms",
        mandatory = true,
        frequency = Frequency.OnlyDailyAtNight,
        measureRes = R.string.symptoms,
        advices = null
    );

    enum class Frequency
    {
        OnlyDailyAtNight,
        OnlyDaily,
        DailyAndOneOff
    }
    
    companion object
    {
        /**
         * Get all [Measure]
         * @return [List] of [Measure]
         */
        fun get(): List<Measure> = Measure.values().asList()

        /**
         * Get all [Measure] that are mandatory
         * @return [List] of [Measure]
         */
        fun getMandatory(): List<Measure> = this.get().filter { it.mandatory }

        /**
         * Get all [Measure] that aren't mandatory
         * @return [List] of [Measure]
         */
        fun getOptional(): List<Measure> = this.get().filter { !it.mandatory }

        /**
         * Get all labels of the optional [Measure] possible values
         * @return [List] of [String] with the labels
         */
        @Composable
        fun getOptionalLabels(): List<String> =
            this.getOptional().map { stringResource(id = it.measureRes) }

        /**
         * Obtain a [Measure] from its id, or null if the string doesn't match a [Measure]
         * @param id to decode
         */
        fun decode(id: String): Measure? =
            when(id)
            {
                Stress.id -> Stress
                Depression.id -> Depression
                Loneliness.id -> Loneliness
                Suicide.id -> Suicide
                Symptoms.id -> Symptoms
                else -> null
            }
    }
}
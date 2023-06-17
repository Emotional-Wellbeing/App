package es.upm.bienestaremocional.data

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.questionnaire.Level

/**
 * Contain the measures that app could monitor, with the frequencies, advices..
 */
enum class Measure(
    val id: String,
    val mandatory : Boolean,
    val frequency: Frequency,
    @StringRes val measureRes: Int,
    val advices : Map<Level,List<Advice>>?
) {
    Stress(
        id = "stress",
        mandatory = true,
        frequency = Frequency.DailyAndOneOff,
        measureRes = R.string.stress,
        advices = mapOf(
            Pair(Level.Low, listOf(
                Advice(
                    head = R.string.low_stress_advice,
                    body = null
                ))
            ),
            Pair(Level.Moderate, listOf(
                Advice(
                    head = R.string.moderate_stress_advice_1_head,
                    body = R.array.moderate_stress_advice_1_body
                ),
                Advice(
                    head = R.string.moderate_stress_advice_2_head,
                    body = R.array.moderate_stress_advice_2_body
                ))
            ),
            Pair(Level.High, listOf(
                Advice(
                    head = R.string.high_stress_advice_head,
                    body = R.array.high_stress_advice_body
                ))
            )
        )
    ),
    Depression(
        id = "depression",
        mandatory = false,
        frequency = Frequency.DailyAndOneOff,
        measureRes = R.string.depression,
        advices = mapOf(
            Pair(Level.Low, listOf(
                Advice(
                    head = R.string.low_depression_advice,
                    body = null
                ))
            ),
            Pair(Level.Moderate, listOf(
                Advice(
                    head = R.string.moderate_depression_advice_head,
                    body = R.array.moderate_depression_advice_body
                ))
            ),
            Pair(Level.High, listOf(
                Advice(
                    head = R.string.high_depression_advice_head,
                    body = R.array.high_depression_advice_body,
                ))
            ),
        )
    ),
    Loneliness(
        id = "loneliness",
        mandatory = false,
        frequency = Frequency.DailyAndOneOff,
        measureRes = R.string.loneliness,
        advices = mapOf(
            Pair(Level.Low, listOf(
                Advice(
                    head = R.string.low_loneliness_advice,
                    body = null
                ))
            ),
            Pair(Level.Moderate, listOf(
                Advice(
                    head = R.string.moderate_loneliness_advice_head,
                    body = R.array.moderate_loneliness_advice_body
                ))
            ),
            Pair(Level.High, listOf(
                Advice(
                    head = R.string.high_loneliness_advice_head,
                    body = R.array.high_loneliness_advice_body,
                ))
            ),
        )
    ),
    Suicide(
        id = "suicide",
        mandatory = true,
        frequency = Frequency.OnlyDaily,
        measureRes = R.string.suicide,
        advices = mapOf(
            Pair(Level.Low, listOf(
                Advice(
                    head = R.string.low_suicide_risk_advice,
                    body = null
                ))
            ),
            Pair(Level.Moderate, listOf(
                Advice(
                    head = R.string.moderate_suicide_risk_advice,
                    body = null
                ))
            ),
            Pair(Level.High, listOf(
                Advice(
                    head = R.string.high_suicide_risk_advice_head,
                    body = R.array.high_suicide_risk_advice_body,
                ))
            ),
        )
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
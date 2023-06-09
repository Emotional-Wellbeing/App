package es.upm.bienestaremocional.domain.usecases

import android.util.Log
import es.upm.bienestaremocional.data.Measure
import es.upm.bienestaremocional.data.database.entity.daily.DailyDepression
import es.upm.bienestaremocional.data.database.entity.daily.DailyLoneliness
import es.upm.bienestaremocional.data.database.entity.daily.DailyStress
import es.upm.bienestaremocional.data.database.entity.daily.DailySuicide
import es.upm.bienestaremocional.data.database.entity.daily.DailySymptoms
import es.upm.bienestaremocional.data.database.entity.round.DailyRound
import es.upm.bienestaremocional.data.settings.AppSettings
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyDepressionRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyLonelinessRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyRoundRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyStressRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailySuicideRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailySymptomsRepository
import kotlinx.coroutines.flow.first

class InsertDailyRoundUseCaseImpl(
    private val logTag: String,
    private val appSettings: AppSettings,
    private val dailyStressRepository: DailyStressRepository,
    private val dailyDepressionRepository: DailyDepressionRepository,
    private val dailyLonelinessRepository: DailyLonelinessRepository,
    private val dailySuicideRepository: DailySuicideRepository,
    private val dailySymptomsRepository: DailySymptomsRepository,
    private val dailyRoundRepository: DailyRoundRepository
) : InsertDailyRoundUseCase
{
    override suspend fun insertDailyMorningRound(): DailyRound
    {
        Log.d(logTag,"Inserting daily morning round")
        //Frequency.OnlyDaily or Frequency.DailyAndOneOff are valid (not Frequency.OnlyDailyAtNight)
        val mandatoryMeasures = Measure.get().filter { it.mandatory &&
                it.frequency != Measure.Frequency.OnlyDailyAtNight
        }
        val optionalMeasures = appSettings.getMeasuresSelected().first().filter {
                it.frequency != Measure.Frequency.OnlyDailyAtNight
        }
        val measures = mandatoryMeasures + optionalMeasures

        var stressId : Long? = null
        var depressionId : Long? = null
        var lonelinessId : Long? = null
        var suicideId : Long? = null

        measures.forEach {
            when(it)
            {
                Measure.Stress -> {
                    stressId = dailyStressRepository.insert(DailyStress())
                }
                Measure.Depression -> {
                    depressionId = dailyDepressionRepository.insert(DailyDepression())
                }
                Measure.Loneliness -> {
                    lonelinessId = dailyLonelinessRepository.insert(DailyLoneliness())
                }
                Measure.Suicide -> {
                    suicideId = dailySuicideRepository.insert(DailySuicide())
                }
                else -> {}
            }
        }
        val dailyRound = DailyRound(
            moment = DailyRound.Moment.Morning,
            stressId = stressId,
            depressionId = depressionId,
            lonelinessId = lonelinessId,
            suicideId = suicideId
        )

        dailyRoundRepository.insert(dailyRound)

        return dailyRound
    }

    override suspend fun insertDailyNightRound(): DailyRound
    {
        Log.d(logTag,"Inserting daily night round")

        // Every frequency is valid
        val mandatoryMeasures = Measure.get().filter { it.mandatory }
        val optionalMeasures = appSettings.getMeasuresSelected().first()
        val measures = mandatoryMeasures + optionalMeasures

        var stressId : Long? = null
        var depressionId : Long? = null
        var lonelinessId : Long? = null
        var suicideId : Long? = null
        var symptomsId : Long? = null

        measures.forEach {
            when(it)
            {
                Measure.Stress -> {
                    stressId = dailyStressRepository.insert(DailyStress())
                }
                Measure.Depression -> {
                    depressionId = dailyDepressionRepository.insert(DailyDepression())
                }
                Measure.Loneliness -> {
                    lonelinessId = dailyLonelinessRepository.insert(DailyLoneliness())
                }
                Measure.Suicide -> {
                    suicideId = dailySuicideRepository.insert(DailySuicide())
                }
                Measure.Symptoms -> {
                    symptomsId = dailySymptomsRepository.insert(DailySymptoms())
                }
            }
        }
        val dailyRound = DailyRound(
            moment = DailyRound.Moment.Night,
            stressId = stressId,
            depressionId = depressionId,
            lonelinessId = lonelinessId,
            suicideId = suicideId,
            symptomsId = symptomsId
        )

        dailyRoundRepository.insert(dailyRound)

        return dailyRound
    }
}
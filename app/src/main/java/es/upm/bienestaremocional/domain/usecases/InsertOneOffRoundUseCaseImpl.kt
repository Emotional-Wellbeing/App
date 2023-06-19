package es.upm.bienestaremocional.domain.usecases

import es.upm.bienestaremocional.data.Measure
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffDepression
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffLoneliness
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffStress
import es.upm.bienestaremocional.data.database.entity.round.OneOffRound
import es.upm.bienestaremocional.data.settings.AppSettings
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffDepressionRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffLonelinessRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffRoundRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffStressRepository
import kotlinx.coroutines.flow.first

class InsertOneOffRoundUseCaseImpl(
    private val logTag: String,
    private val appSettings: AppSettings,
    private val oneOffStressRepository: OneOffStressRepository,
    private val oneOffDepressionRepository: OneOffDepressionRepository,
    private val oneOffLonelinessRepository: OneOffLonelinessRepository,
    private val oneOffRoundRepository: OneOffRoundRepository
) : InsertOneOffRoundUseCase {
    override suspend fun insertOneOffRound(): OneOffRound {
        val mandatoryMeasures = Measure.get().filter {
            it.mandatory &&
                    it.frequency == Measure.Frequency.DailyAndOneOff
        }
        val optionalMeasures = appSettings.getMeasuresSelected().first().filter {
            it.frequency == Measure.Frequency.DailyAndOneOff
        }
        val measures = mandatoryMeasures + optionalMeasures

        var stressId: Long? = null
        var depressionId: Long? = null
        var lonelinessId: Long? = null

        measures.forEach {
            when (it) {
                Measure.Stress -> {
                    stressId = oneOffStressRepository.insert(OneOffStress())
                }

                Measure.Depression -> {
                    depressionId = oneOffDepressionRepository.insert(OneOffDepression())
                }

                Measure.Loneliness -> {
                    lonelinessId = oneOffLonelinessRepository.insert(OneOffLoneliness())
                }

                else -> {}
            }
        }
        val oneOffRound = OneOffRound(
            stressId = stressId,
            depressionId = depressionId,
            lonelinessId = lonelinessId
        )
        oneOffRoundRepository.insert(oneOffRound)

        return oneOffRound
    }

}
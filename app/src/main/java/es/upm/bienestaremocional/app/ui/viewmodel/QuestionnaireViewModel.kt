package es.upm.bienestaremocional.app.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.database.entity.PHQ
import es.upm.bienestaremocional.app.data.database.entity.PSS
import es.upm.bienestaremocional.app.data.database.entity.UCLA
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PHQRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PSSRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.UCLARepository
import es.upm.bienestaremocional.app.ui.screen.destinations.QuestionnaireScreenDestination
import es.upm.bienestaremocional.app.ui.state.QuestionnaireState
import es.upm.bienestaremocional.app.utils.decodeScoreLevel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class QuestionnaireViewModel @Inject constructor(
    private val pssRepository: PSSRepository,
    private val phqRepository: PHQRepository,
    private val uclaRepository: UCLARepository,
    savedStateHandle: SavedStateHandle,
    @Named("logTag") val logTag : String
) : ViewModel()
{
    //state
    private val _state : MutableStateFlow<QuestionnaireState> = MutableStateFlow(QuestionnaireState.InProgress)
    val state : StateFlow<QuestionnaireState> get() = _state.asStateFlow()

    private val questionnaire = QuestionnaireScreenDestination.argsFrom(savedStateHandle).questionnaire
    private val entityId = QuestionnaireScreenDestination.argsFrom(savedStateHandle).entityId

    var pss: PSS? = null
    var phq: PHQ? = null
    var ucla: UCLA? = null

    private val answers: Array<Int?> = arrayOfNulls(questionnaire.numberOfQuestions)

    init {
        runBlocking {
            when(questionnaire)
            {
                Questionnaire.PSS -> { pss = pssRepository.get(entityId) }
                Questionnaire.PHQ -> { phq = phqRepository.get(entityId) }
                Questionnaire.UCLA -> { ucla = uclaRepository.get(entityId) }
            }
        }
        loadAnswers()
    }

    val answersRemaining: List<Int>
        get() = answers.mapIndexed{index, value -> if(value == null) index else null}.filterNotNull()

    private val questionnaireFulfilled: Boolean
        get() = answers.all { it != null }

    val score : Int?
        get()
        {
            return if (questionnaireFulfilled)
            {
                var auxiliarScore = 0
                answers.forEachIndexed { index, answer ->
                    var tempScore = (answer?.plus(questionnaire.questionScoreOffset)!!)
                    if (questionnaire.questionsWithInvertedScore.contains(index))
                        tempScore = questionnaire.numberOfQuestions - 1 - tempScore
                    auxiliarScore += tempScore
                }
                auxiliarScore
            }
            else
                null
        }

    private val scoreLevel : String?
        get()
        {
            return if (questionnaireFulfilled)
            {
                var scoreLevel: String? = null
                for(level in questionnaire.levels)
                {
                    if (score in level.min .. level.max)
                    {
                        scoreLevel = level.internalLabel
                        break
                    }
                }
                scoreLevel
            }
            else
                null
        }

    val scoreLevelRes : Int?
        get() = decodeScoreLevel(scoreLevel,questionnaire)

    private fun setAnswer(questionIndex: Int, answerIndex: Int) {
        if (questionIndex < questionnaire.numberOfQuestions)
            answers[questionIndex] = answerIndex
    }

    fun getAnswer(questionIndex: Int): Int? {
        return if (questionIndex < questionnaire.numberOfQuestions)
            answers[questionIndex]
        else
            null
    }

    private fun removeAnswer(questionIndex: Int) {
        if (questionIndex < questionnaire.numberOfQuestions)
            answers[questionIndex] = null
    }

    fun onSkippingAttempt()
    {
        _state.value = QuestionnaireState.SkipAttempt
    }

    fun onInProgress()
    {
        _state.value = QuestionnaireState.InProgress
    }

    fun onSkipped()
    {
        runBlocking {
            updateQuestionnaire()
        }
        _state.value = QuestionnaireState.Skipped
    }

    fun onFinishAttempt()
    {
        val listAnswersRemaining = answersRemaining
        if (listAnswersRemaining.isEmpty())
            _state.value = QuestionnaireState.Summary
        else
            _state.value = QuestionnaireState.FinishAttempt
    }

    fun onSummary()
    {
        runBlocking {
            updateQuestionnaire()
        }
        _state.value = QuestionnaireState.Finished
    }

    fun onAnswer(question: Int, answer: Int)
    {
        if (getAnswer(question) != answer)
            setAnswer(question,answer)
        else
            removeAnswer(question)
    }

    private fun loadAnswers()
    {
        pss?.let {
            getAnswers(it)
        } ?: run {
            //phq not null
            phq?.let {
                getAnswers(it)
            } ?: run {
                //ucla not null
                ucla?.let {
                    getAnswers(it)
                }
            }
        }
    }

    private suspend fun updateQuestionnaire()
    {
        //pss not null
        pss?.let {
            setPSS(it)
            pssRepository.update(it)
        } ?: run {
            //phq not null
            phq?.let {
                setPHQ(it)
                phqRepository.update(it)
            } ?: run {
                //ucla not null
                ucla?.let {
                    setUCLA(it)
                    uclaRepository.update(it)
                }
            }
        }
    }

    private fun getAnswers(pss: PSS)
    {
        answers[0] = pss.answer1
        answers[1] = pss.answer2
        answers[2] = pss.answer3
        answers[3] = pss.answer4
        answers[4] = pss.answer5
        answers[5] = pss.answer6
        answers[6] = pss.answer7
        answers[7] = pss.answer8
        answers[8] = pss.answer9
        answers[9] = pss.answer10
    }

    private fun getAnswers(phq: PHQ)
    {
        answers[0] = phq.answer1
        answers[1] = phq.answer2
        answers[2] = phq.answer3
        answers[3] = phq.answer4
        answers[4] = phq.answer5
        answers[5] = phq.answer6
        answers[6] = phq.answer7
        answers[7] = phq.answer8
        answers[8] = phq.answer9
    }

    private fun getAnswers(ucla: UCLA)
    {
        answers[0] = ucla.answer1
        answers[1] = ucla.answer2
        answers[2] = ucla.answer3
        answers[3] = ucla.answer4
        answers[4] = ucla.answer5
        answers[5] = ucla.answer6
        answers[6] = ucla.answer7
        answers[7] = ucla.answer8
        answers[8] = ucla.answer9
        answers[9] = ucla.answer10
        answers[10] = ucla.answer11
        answers[11] = ucla.answer12
        answers[12] = ucla.answer13
        answers[13] = ucla.answer14
        answers[14] = ucla.answer15
        answers[15] = ucla.answer16
        answers[16] = ucla.answer17
        answers[17] = ucla.answer18
        answers[18] = ucla.answer19
        answers[19] = ucla.answer20
    }

    private fun setPSS(pss: PSS) {
        pss.apply {
            score = this@QuestionnaireViewModel.score
            scoreLevel = this@QuestionnaireViewModel.scoreLevel
            completed = this@QuestionnaireViewModel.questionnaireFulfilled
            answer1 = this@QuestionnaireViewModel.answers.getOrNull(0)
            answer2 = this@QuestionnaireViewModel.answers.getOrNull(1)
            answer3 = this@QuestionnaireViewModel.answers.getOrNull(2)
            answer4 = this@QuestionnaireViewModel.answers.getOrNull(3)
            answer5 = this@QuestionnaireViewModel.answers.getOrNull(4)
            answer6 = this@QuestionnaireViewModel.answers.getOrNull(5)
            answer7 = this@QuestionnaireViewModel.answers.getOrNull(6)
            answer8 = this@QuestionnaireViewModel.answers.getOrNull(7)
            answer9 = this@QuestionnaireViewModel.answers.getOrNull(8)
            answer10 = this@QuestionnaireViewModel.answers.getOrNull(9)
        }
    }

    private fun setPHQ(phq: PHQ) {
        phq.apply {
            score = this@QuestionnaireViewModel.score
            scoreLevel = this@QuestionnaireViewModel.scoreLevel
            completed = this@QuestionnaireViewModel.questionnaireFulfilled
            answer1 = this@QuestionnaireViewModel.answers.getOrNull(0)
            answer2 = this@QuestionnaireViewModel.answers.getOrNull(1)
            answer3 = this@QuestionnaireViewModel.answers.getOrNull(2)
            answer4 = this@QuestionnaireViewModel.answers.getOrNull(3)
            answer5 = this@QuestionnaireViewModel.answers.getOrNull(4)
            answer6 = this@QuestionnaireViewModel.answers.getOrNull(5)
            answer7 = this@QuestionnaireViewModel.answers.getOrNull(6)
            answer8 = this@QuestionnaireViewModel.answers.getOrNull(7)
            answer9 = this@QuestionnaireViewModel.answers.getOrNull(8)
        }
    }

    private fun setUCLA(ucla: UCLA) {
        ucla.apply {
            score = this@QuestionnaireViewModel.score
            scoreLevel = this@QuestionnaireViewModel.scoreLevel
            completed = this@QuestionnaireViewModel.questionnaireFulfilled
            answer1 = this@QuestionnaireViewModel.answers.getOrNull(0)
            answer2 = this@QuestionnaireViewModel.answers.getOrNull(1)
            answer3 = this@QuestionnaireViewModel.answers.getOrNull(2)
            answer4 = this@QuestionnaireViewModel.answers.getOrNull(3)
            answer5 = this@QuestionnaireViewModel.answers.getOrNull(4)
            answer6 = this@QuestionnaireViewModel.answers.getOrNull(5)
            answer7 = this@QuestionnaireViewModel.answers.getOrNull(6)
            answer8 = this@QuestionnaireViewModel.answers.getOrNull(7)
            answer9 = this@QuestionnaireViewModel.answers.getOrNull(8)
            answer10 = this@QuestionnaireViewModel.answers.getOrNull(9)
            answer11 = this@QuestionnaireViewModel.answers.getOrNull(10)
            answer12 = this@QuestionnaireViewModel.answers.getOrNull(11)
            answer13 = this@QuestionnaireViewModel.answers.getOrNull(12)
            answer14 = this@QuestionnaireViewModel.answers.getOrNull(13)
            answer15 = this@QuestionnaireViewModel.answers.getOrNull(14)
            answer16 = this@QuestionnaireViewModel.answers.getOrNull(15)
            answer17 = this@QuestionnaireViewModel.answers.getOrNull(16)
            answer18 = this@QuestionnaireViewModel.answers.getOrNull(17)
            answer19 = this@QuestionnaireViewModel.answers.getOrNull(18)
            answer20 = this@QuestionnaireViewModel.answers.getOrNull(19)
        }
    }
}
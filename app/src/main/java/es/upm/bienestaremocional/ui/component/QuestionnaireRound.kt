package es.upm.bienestaremocional.ui.component

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.database.entity.QuestionnaireRoundFull
import es.upm.bienestaremocional.data.questionnaire.Level
import es.upm.bienestaremocional.utils.formatUnixTimeStamp

/**
 * Display [QuestionnaireRoundFull]: createdAt, score and level
 */
@Composable
fun ShowQuestionnaireRound(element : QuestionnaireRoundFull)
{
    val context = LocalContext.current

    Text(context.getString(R.string.round_created_formatter,
        formatUnixTimeStamp(element.questionnaireRound.createdAt)
    ))

    element.pss.let {
        Text(stringResource(R.string.pss_questionnaire))
        if (it.completed)
        {
            Text(context.getString(R.string.score_formatter,it.score))
            it.scoreLevel?.let { scoreLevel -> ShowScoreLevel(context, scoreLevel) }
        }
        else
            Text(stringResource(R.string.uncompleted))
    }

    element.phq?.let {
        Text(stringResource(R.string.phq_questionnaire))
        if (it.completed)
        {
            Text(context.getString(R.string.score_formatter,it.score))
            it.scoreLevel?.let { scoreLevel -> ShowScoreLevel(context, scoreLevel) }
        }
        else
            Text(stringResource(R.string.uncompleted))
    }

    element.ucla?.let {
        Text(stringResource(R.string.ucla_questionnaire))
        if (it.completed)
        {
            Text(context.getString(R.string.score_formatter,it.score))
            it.scoreLevel?.let { scoreLevel -> ShowScoreLevel(context, scoreLevel) }
        }
        else
            Text(stringResource(R.string.uncompleted))
    }
}

/**
 * Display uncompleted [QuestionnaireRoundFull]: createdAt and uncompleted questionnaires
 */
@Composable
fun ShowUncompletedQuestionnaireRound(element : QuestionnaireRoundFull)
{
    val uncompletedQuestionnairesText = stringResource(R.string.uncompleted_questionnaires_label)

    val uncompleted = mutableListOf<String>()

    element.pss.let {
        if (!it.completed)
            uncompleted.add(stringResource(id = R.string.stress))
    }

    element.phq?.let {
        if (!it.completed)
            uncompleted.add(stringResource(id = R.string.depression))
    }

    element.ucla?.let {
        if (!it.completed)
            uncompleted.add(stringResource(id = R.string.loneliness))
    }

    Text(stringResource(R.string.created_formatter,
        formatUnixTimeStamp(element.questionnaireRound.createdAt)
    ))

    Text("$uncompletedQuestionnairesText:  ${uncompleted.joinToString()}")

}

@Composable
private fun ShowScoreLevel(context: Context, scoreLevel: String)
{
    Level.decodeFromId(scoreLevel)?.let {
        val label = stringResource(it.label)
        Text(context.getString(R.string.level_formatter,label))
    }
}
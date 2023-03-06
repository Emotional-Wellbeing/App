package es.upm.bienestaremocional.app.ui.component

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRoundFull
import es.upm.bienestaremocional.app.data.questionnaire.LevelLabel
import es.upm.bienestaremocional.app.utils.formatUnixTimeStamp

/**
 * Display [QuestionnaireRoundFull]: createdAt, score and level
 */
@Composable
fun ShowQuestionnaireRound(element : QuestionnaireRoundFull)
{
    val context = LocalContext.current

    Text(context.getString(R.string.round_created_formatter,
        formatUnixTimeStamp(element.questionnaireRound.createdAt)))

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

@Composable
private fun ShowScoreLevel(context: Context, scoreLevel: String)
{
    LevelLabel.decodeFromId(scoreLevel)?.let {
        val label = stringResource(it.label)
        Text(context.getString(R.string.level_formatter,label))
    }
}
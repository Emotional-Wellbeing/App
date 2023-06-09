package es.upm.bienestaremocional.ui.component

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.database.entity.round.DailyRound
import es.upm.bienestaremocional.data.database.entity.round.DailyRoundFull
import es.upm.bienestaremocional.data.questionnaire.Level
import es.upm.bienestaremocional.utils.formatUnixTimeStamp

/**
 * Display [DailyRoundFull]: createdAt, score and level
 */
@Composable
fun ShowDailyRound(element : DailyRoundFull)
{
    val context = LocalContext.current

    val momentRes = if (element.dailyRound.moment == DailyRound.Moment.Morning)
        R.string.daily_morning
    else
        R.string.daily_night

    Text(
        context.getString(
            R.string.round_created_formatter,
            stringResource(id = momentRes),
            formatUnixTimeStamp(element.dailyRound.createdAt)
        )
    )

    element.dailyStress?.let {
        Text(stringResource(R.string.stress))
        if (it.completed)
        {
            Text(context.getString(R.string.score_formatter,it.score))
            it.scoreLevel?.let { scoreLevel -> ShowScoreLevel(context, scoreLevel) }
        }
        else
            Text(stringResource(R.string.uncompleted))
    }

    element.dailyDepression?.let {
        Text(stringResource(R.string.depression))
        if (it.completed)
        {
            Text(context.getString(R.string.score_formatter,it.score))
            it.scoreLevel?.let { scoreLevel -> ShowScoreLevel(context, scoreLevel) }
        }
        else
            Text(stringResource(R.string.uncompleted))
    }

    element.dailyLoneliness?.let {
        Text(stringResource(R.string.loneliness))
        if (it.completed)
        {
            Text(context.getString(R.string.score_formatter,it.score))
            it.scoreLevel?.let { scoreLevel -> ShowScoreLevel(context, scoreLevel) }
        }
        else
            Text(stringResource(R.string.uncompleted))
    }

    element.dailySuicide?.let {
        Text(stringResource(R.string.suicide))
        if (it.completed)
            Text(stringResource(R.string.completed))
        else
            Text(stringResource(R.string.uncompleted))
    }

    element.dailySymptoms?.let {
        Text(stringResource(R.string.symptoms))
        if (it.completed)
            Text(stringResource(R.string.completed))
        else
            Text(stringResource(R.string.uncompleted))
    }
}

/**
 * Display uncompleted [DailyRoundFull]: createdAt and uncompleted questionnaires
 */
@Composable
fun ShowUncompletedDailyRound(element : DailyRoundFull)
{
    val uncompletedQuestionnairesText = stringResource(R.string.uncompleted_questionnaires_label)

    val uncompleted = mutableListOf<String>()

    element.dailyStress?.let {
        if (!it.completed)
            uncompleted.add(stringResource(id = R.string.stress))
    }

    element.dailyDepression?.let {
        if (!it.completed)
            uncompleted.add(stringResource(id = R.string.depression))
    }

    element.dailyLoneliness?.let {
        if (!it.completed)
            uncompleted.add(stringResource(id = R.string.loneliness))
    }

    element.dailySuicide?.let {
        if (!it.completed)
            uncompleted.add(stringResource(id = R.string.suicide))
    }

    element.dailySymptoms?.let {
        if (!it.completed)
            uncompleted.add(stringResource(id = R.string.symptoms))
    }

    Text(stringResource(R.string.created_formatter,
        formatUnixTimeStamp(element.dailyRound.createdAt)
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
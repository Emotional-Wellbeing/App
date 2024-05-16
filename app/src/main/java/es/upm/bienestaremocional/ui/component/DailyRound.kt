package es.upm.bienestaremocional.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.database.entity.round.DailyRound
import es.upm.bienestaremocional.data.database.entity.round.DailyRoundFull
import es.upm.bienestaremocional.utils.formatUnixTimeStamp

/**
 * Display uncompleted [DailyRoundFull]: createdAt and uncompleted questionnaires
 */
@Composable
fun ShowUncompletedDailyRound(element: DailyRoundFull) {
    val context = LocalContext.current

    val uncompletedQuestionnairesText = stringResource(R.string.uncompleted_questionnaires_label)

    val uncompleted = mutableListOf<String>()

    val momentRes = if (element.dailyRound.moment == DailyRound.Moment.Morning)
        R.string.daily_morning
    else
        R.string.daily_night

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

    Text(
        context.getString(
            R.string.round_created_formatter,
            stringResource(id = momentRes),
            formatUnixTimeStamp(element.dailyRound.createdAt)
        )
    )

    Text("$uncompletedQuestionnairesText:  ${uncompleted.joinToString()}")

}
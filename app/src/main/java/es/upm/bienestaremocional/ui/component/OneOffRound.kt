package es.upm.bienestaremocional.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.database.entity.round.OneOffRoundFull
import es.upm.bienestaremocional.utils.formatUnixTimeStamp

/**
 * Display uncompleted [OneOffRoundFull]: createdAt and uncompleted questionnaires
 */
@Composable
fun ShowUncompletedOneOffRound(element: OneOffRoundFull) {
    val context = LocalContext.current

    val uncompletedQuestionnairesText = stringResource(R.string.uncompleted_questionnaires_label)

    val uncompleted = mutableListOf<String>()

    element.oneOffStress.let {
        if (!it.completed)
            uncompleted.add(stringResource(id = R.string.stress))
    }

    element.oneOffDepression?.let {
        if (!it.completed)
            uncompleted.add(stringResource(id = R.string.depression))
    }

    element.oneOffLoneliness?.let {
        if (!it.completed)
            uncompleted.add(stringResource(id = R.string.loneliness))
    }

    Text(
        context.getString(
            R.string.round_created_formatter,
            stringResource(id = R.string.one_off),
            formatUnixTimeStamp(element.oneOffRound.createdAt)
        )
    )

    Text("$uncompletedQuestionnairesText: ${uncompleted.joinToString()}")

}
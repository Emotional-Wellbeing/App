package es.upm.bienestaremocional.ui.navigation

import com.ramcosta.composedestinations.navargs.DestinationsNavTypeSerializer
import com.ramcosta.composedestinations.navargs.NavTypeSerializer
import es.upm.bienestaremocional.data.database.entity.QuestionnaireRoundReduced

/**
 * Serializer to pass [QuestionnaireRoundReduced] at Destinations
 */
@NavTypeSerializer
class QuestionnaireRoundReducedNavTypeSerializer :
    DestinationsNavTypeSerializer<QuestionnaireRoundReduced>
{
    override fun fromRouteString(routeStr: String): QuestionnaireRoundReduced {
        val ids = routeStr.split(";").map { it.toLongOrNull() }
        return QuestionnaireRoundReduced(ids[0]!!, ids[1], ids[2], ids[3])
    }

    override fun toRouteString(value: QuestionnaireRoundReduced): String {
        return "${value.qrId};${value.pssId};${value.phqId};${value.uclaId}"
    }

}
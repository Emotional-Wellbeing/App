package es.upm.bienestaremocional.ui.navigation

import com.ramcosta.composedestinations.navargs.DestinationsNavTypeSerializer
import com.ramcosta.composedestinations.navargs.NavTypeSerializer
import es.upm.bienestaremocional.data.database.entity.round.DailyRound

/**
 * Serializer to pass [DailyRound] at Destinations
 */
@NavTypeSerializer
class DailyRoundNavTypeSerializer :
    DestinationsNavTypeSerializer<DailyRound>
{
    override fun fromRouteString(routeStr: String): DailyRound {
        val values = routeStr.split(";").map { it.toLongOrNull() }
        return DailyRound(
            id = values[0]!!,
            createdAt = values[1]!!,
            modifiedAt = values[2]!!,
            moment = DailyRound.Moment[values[3]!!.toInt()]!!,
            stressId = values[4],
            depressionId = values[5],
            lonelinessId = values[6],
            suicideId = values[7],
            symptomsId = values[8],
        )
    }

    override fun toRouteString(value: DailyRound): String {
        return "${value.id};${value.createdAt};${value.modifiedAt};${value.moment.ordinal};" +
                "${value.stressId};${value.depressionId};${value.lonelinessId};" +
                "${value.suicideId};${value.symptomsId};"
    }

}
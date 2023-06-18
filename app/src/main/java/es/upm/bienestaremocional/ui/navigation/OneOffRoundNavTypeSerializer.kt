package es.upm.bienestaremocional.ui.navigation

import com.ramcosta.composedestinations.navargs.DestinationsNavTypeSerializer
import com.ramcosta.composedestinations.navargs.NavTypeSerializer
import es.upm.bienestaremocional.data.database.entity.round.OneOffRound

/**
 * Serializer to pass [OneOffRound] at Destinations
 */
@NavTypeSerializer
class OneOffRoundNavTypeSerializer :
    DestinationsNavTypeSerializer<OneOffRound> {
    override fun fromRouteString(routeStr: String): OneOffRound {
        val values = routeStr.split(";").map { it.toLongOrNull() }
        return OneOffRound(
            id = values[0]!!,
            createdAt = values[1]!!,
            modifiedAt = values[2]!!,
            stressId = values[3],
            depressionId = values[4],
            lonelinessId = values[5],
        )
    }

    override fun toRouteString(value: OneOffRound): String {
        return "${value.id};${value.createdAt};${value.modifiedAt};" +
                "${value.stressId};${value.depressionId};${value.lonelinessId}"
    }

}
package es.upm.bienestaremocional.ui.navigation

import com.ramcosta.composedestinations.navargs.DestinationsNavTypeSerializer
import com.ramcosta.composedestinations.navargs.NavTypeSerializer
import es.upm.bienestaremocional.data.Advice

/**
 * Serializer to pass [Advice] at Destinations
 */
@NavTypeSerializer
class AdviceNavTypeSerializer :
    DestinationsNavTypeSerializer<Advice> {
    override fun fromRouteString(routeStr: String): Advice {
        val values = routeStr.split(";").map { it.toIntOrNull() }
        return Advice(
            head = values[0]!!,
            body = values.getOrNull(1),
        )
    }

    override fun toRouteString(value: Advice): String {
        return "${value.head};${value.body};"
    }

}
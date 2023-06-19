package es.upm.bienestaremocional.data

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes

data class Advice(
    @StringRes val head: Int,
    @ArrayRes val body: Int?
)

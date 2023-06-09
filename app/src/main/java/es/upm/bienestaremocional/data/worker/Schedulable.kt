package es.upm.bienestaremocional.data.worker

import java.time.Duration
import java.time.LocalDateTime

interface Schedulable
{
    val time: LocalDateTime
    val tag : String
    val repeatInterval : Duration
}
package es.upm.bienestaremocional.data.worker

import java.time.Duration
import java.time.LocalDateTime

/**
 * Contains the essential data of a process that should be scheduled
 */
interface Schedulable
{
    val time: LocalDateTime
    val tag : String
    val repeatInterval : Duration
}
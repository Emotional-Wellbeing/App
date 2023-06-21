package es.upm.bienestaremocional.data.worker

/**
 * Contains the essential data of a process that should only executed one time
 */
interface OneTimeWorker {
    val tag: String
}
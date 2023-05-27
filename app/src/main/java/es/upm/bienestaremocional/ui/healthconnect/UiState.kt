package es.upm.bienestaremocional.ui.healthconnect

import java.util.UUID

/**
 * Contains states used for display HealthConnect data
 */
sealed class UiState
{
    object Uninitialized : UiState()
    object Success : UiState()
    object NotEnoughPermissions : UiState()

    // A random UUID is used in each Error object to allow errors to be uniquely identified,
    // and recomposition won't result in multiple snackbars.
    data class Error(val exception: Throwable, val uuid: UUID = UUID.randomUUID()) : UiState()
}

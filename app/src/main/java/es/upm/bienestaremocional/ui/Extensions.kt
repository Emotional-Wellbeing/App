package es.upm.bienestaremocional.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry

@Composable
fun <T> NavBackStackEntry.GetOnceResult(keyResult: String, onResult: (T) -> Unit) {
    //we don't use getLiveData because it tends to loop if we have states on the screen.
    val result = savedStateHandle.remove<T>(keyResult)
    result?.let { onResult(it) }
}
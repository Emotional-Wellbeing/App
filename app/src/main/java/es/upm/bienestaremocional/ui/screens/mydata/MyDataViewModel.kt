package es.upm.bienestaremocional.ui.screens.mydata

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.utils.showExceptionSnackbar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class MyDataViewModel @Inject constructor(
    @Named("logTag") private val logTag: String
) : ViewModel() {
    //state
    private val _state = MutableStateFlow<MyDataState>(MyDataState.NoSelection)
    val state: StateFlow<MyDataState> = _state.asStateFlow()

    fun onSelect(index: Int) {
        _state.value = MyDataState.Selected(index)
    }

    fun onUnselect() {
        _state.value = MyDataState.NoSelection
    }

    suspend fun onError(snackbarHostState: SnackbarHostState, exception: Throwable?) {
        Log.e(logTag, "Exception on MyDataViewModel", exception)
        showExceptionSnackbar(snackbarHostState, exception)
    }

}
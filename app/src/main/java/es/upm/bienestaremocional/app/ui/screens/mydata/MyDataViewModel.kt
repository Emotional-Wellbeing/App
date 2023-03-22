package es.upm.bienestaremocional.app.ui.screens.mydata

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.utils.showExceptionSnackbar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class MyDataViewModel @Inject constructor() : ViewModel()
{
    //state
    private val _state = MutableStateFlow<MyDataState>(MyDataState.NoSelection)
    val state: StateFlow<MyDataState> = _state.asStateFlow()

    fun onSelect(index: Int)
    {
        _state.value = MyDataState.Selected(index)
    }

    fun onUnselect()
    {
        _state.value = MyDataState.NoSelection
    }
    suspend fun onError(snackbarHostState: SnackbarHostState, exception: Throwable?) =
        showExceptionSnackbar(snackbarHostState, exception)
}
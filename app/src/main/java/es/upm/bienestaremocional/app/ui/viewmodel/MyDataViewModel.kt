package es.upm.bienestaremocional.app.ui.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import es.upm.bienestaremocional.app.utils.showExceptionSnackbar

class MyDataViewModelFactory(private val snackbarHostState: SnackbarHostState) :
    ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(MyDataViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return MyDataViewModel(snackbarHostState) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MyDataViewModel(private val snackbarHostState: SnackbarHostState) : ViewModel()
{
    fun onError(exception: Throwable?)
    {
        showExceptionSnackbar(
            viewModelScope,
            snackbarHostState,
            exception
        )
    }
}
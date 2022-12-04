package es.upm.bienestaremocional.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.records.Record
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.UiState
import java.util.*

@Composable
fun DrawHealthConnectScreen(viewModelData: ViewModelData<out Record>,
                            onDisplayData: LazyListScope.() -> Unit,
                            onError: (Throwable?) -> Unit = {})
{
    // Remember the last error ID, such that it is possible to avoid re-launching the error
    // notification for the same error when the screen is recomposed, or configuration changes etc.
    val errorId = rememberSaveable { mutableStateOf(UUID.randomUUID()) }

    LaunchedEffect(viewModelData.uiState)
    {
        // If the initial data load has not taken place, attempt to load the data.
        if (viewModelData.uiState is UiState.Uninitialized)
            viewModelData.onPermissionsResult()

        // The UiState provides details of whether the last action was a
        // success or resulted in an error. Where an error occurred, for example in reading and
        // writing to Health Connect, the user is notified, and where the error is one that can be
        // recovered from, an attempt to do so is made.
        if (viewModelData.uiState is UiState.Error && errorId.value != viewModelData.uiState.uuid)
        {
            onError(viewModelData.uiState.exception)
            errorId.value = viewModelData.uiState.uuid
        }
    }
    if (viewModelData.uiState != UiState.Uninitialized)
    {
        Surface {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                if (viewModelData.uiState == UiState.Success)
                {
                    if (viewModelData.data.isEmpty())
                    {
                        item {
                            Button(onClick = viewModelData.onWrite)
                            {
                                Text(text = "Generar datos")
                            }
                        }
                    }
                    else
                        onDisplayData()
                }
                else if (viewModelData.uiState == UiState.NotEnoughPermissions)
                {
                    item {
                        Button(onClick = {
                            viewModelData.onRequestPermissions(viewModelData.permissions) })
                        {
                            Text(text = "Solicita permisos")
                        }
                    }
                }
            }
        }
    }
}
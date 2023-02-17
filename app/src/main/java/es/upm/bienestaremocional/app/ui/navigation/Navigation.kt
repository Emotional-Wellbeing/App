package es.upm.bienestaremocional.app.ui.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import es.upm.bienestaremocional.app.ui.screen.NavGraphs
import es.upm.bienestaremocional.app.ui.screen.destinations.MyDataScreenDestination
import es.upm.bienestaremocional.app.ui.viewmodel.MyDataViewModel
import es.upm.bienestaremocional.app.ui.viewmodel.MyDataViewModelFactory

/**
 * Manages the navigation in the app
 */

@Composable
fun AppNavigation()
{
    val snackbarHostState = remember { SnackbarHostState() }

    DestinationsNavHost(navGraph = NavGraphs.root, dependenciesContainerBuilder = {

        dependency(MyDataScreenDestination)
        {
            viewModel<MyDataViewModel>(factory = MyDataViewModelFactory(snackbarHostState))
        }
    })
}
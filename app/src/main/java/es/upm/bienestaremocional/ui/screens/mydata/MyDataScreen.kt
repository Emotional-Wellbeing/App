package es.upm.bienestaremocional.ui.screens.mydata

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.records.ElevationGainedRecord
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.health.connect.client.records.FloorsClimbedRecord
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.records.WeightRecord
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.healthconnect.types.SleepSessionData
import es.upm.bienestaremocional.ui.component.AppBasicScreen
import es.upm.bienestaremocional.ui.component.DrawHealthConnectSubscreen
import es.upm.bienestaremocional.ui.component.ViewModelData
import es.upm.bienestaremocional.ui.healthconnect.component.Display
import es.upm.bienestaremocional.ui.healthconnect.viewmodel.DistanceViewModel
import es.upm.bienestaremocional.ui.healthconnect.viewmodel.ElevationGainedViewModel
import es.upm.bienestaremocional.ui.healthconnect.viewmodel.ExerciseSessionViewModel
import es.upm.bienestaremocional.ui.healthconnect.viewmodel.FloorsClimbedViewModel
import es.upm.bienestaremocional.ui.healthconnect.viewmodel.HeartRateViewModel
import es.upm.bienestaremocional.ui.healthconnect.viewmodel.SleepSessionViewModel
import es.upm.bienestaremocional.ui.healthconnect.viewmodel.StepsViewModel
import es.upm.bienestaremocional.ui.healthconnect.viewmodel.TotalCaloriesBurnedViewModel
import es.upm.bienestaremocional.ui.healthconnect.viewmodel.WeightViewModel
import es.upm.bienestaremocional.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.ui.responsive.computeWindowWidthSize
import kotlinx.coroutines.launch

@Destination
@Composable
fun MyDataScreen(
    navigator: DestinationsNavigator,
    viewModel: MyDataViewModel = hiltViewModel(),
    sleepSessionViewModel: SleepSessionViewModel = hiltViewModel(),
    heartRateViewModel: HeartRateViewModel = hiltViewModel(),
    stepsViewModel: StepsViewModel = hiltViewModel(),
    distanceViewModel: DistanceViewModel = hiltViewModel(),
    totalCaloriesBurnedViewModel: TotalCaloriesBurnedViewModel = hiltViewModel(),
    elevationGainedViewModel: ElevationGainedViewModel = hiltViewModel(),
    exerciseSessionViewModel: ExerciseSessionViewModel = hiltViewModel(),
    floorsClimbedViewModel: FloorsClimbedViewModel = hiltViewModel(),
    weightViewModel: WeightViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsStateWithLifecycle()

    DrawMyDataScreen(
        navigator = navigator,
        widthSize = computeWindowWidthSize(),
        snackbarHostState = snackbarHostState,
        state = state,
        sleepVMD = sleepSessionViewModel.getViewModelData(),
        heartRateVMD = heartRateViewModel.getViewModelData(),
        stepsVMD = stepsViewModel.getViewModelData(),
        distanceVMD = distanceViewModel.getViewModelData(),
        totalCaloriesBurnedVMD = totalCaloriesBurnedViewModel.getViewModelData(),
        exerciseSessionVMD = exerciseSessionViewModel.getViewModelData(),
        elevationGainedVMD = elevationGainedViewModel.getViewModelData(),
        floorsClimbedVMD = floorsClimbedViewModel.getViewModelData(),
        weightVMD = weightViewModel.getViewModelData(),
        onSelect = { index -> viewModel.onSelect(index) },
        onUnselect = { viewModel.onUnselect() },
        onError = { exception -> viewModel.onError(snackbarHostState, exception) })
}

@Composable
private fun CategoryText(
    @StringRes stringRes: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f),
            text = stringResource(id = stringRes),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
        IconButton(onClick = onClick)
        {
            val icon = if (selected)
                Icons.Default.KeyboardArrowDown
            else
                Icons.AutoMirrored.Filled.KeyboardArrowRight
            Icon(icon, contentDescription = "Expandir")
        }
    }
}

@Composable
private fun DrawMyDataScreen(
    navigator: DestinationsNavigator,
    widthSize: WindowWidthSizeClass,
    snackbarHostState: SnackbarHostState,
    state: MyDataState,
    sleepVMD: ViewModelData<SleepSessionData>,
    heartRateVMD: ViewModelData<HeartRateRecord>,
    stepsVMD: ViewModelData<StepsRecord>,
    distanceVMD: ViewModelData<DistanceRecord>,
    totalCaloriesBurnedVMD: ViewModelData<TotalCaloriesBurnedRecord>,
    exerciseSessionVMD: ViewModelData<ExerciseSessionRecord>,
    elevationGainedVMD: ViewModelData<ElevationGainedRecord>,
    floorsClimbedVMD: ViewModelData<FloorsClimbedRecord>,
    weightVMD: ViewModelData<WeightRecord>,
    onSelect: (Int) -> Unit,
    onUnselect: () -> Unit,
    onError: suspend (Throwable?) -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()

    AppBasicScreen(
        navigator = navigator,
        entrySelected = BottomBarDestination.SettingsScreen,
        label = R.string.my_data_label,
        snackbarHostState = snackbarHostState
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            when (state) {
                is MyDataState.NoSelection -> {
                    CategoryText(stringRes = R.string.sleep,
                        selected = false,
                        onClick = { onSelect(0) })
                    CategoryText(stringRes = R.string.heart_rate,
                        selected = false,
                        onClick = { onSelect(1) })
                    CategoryText(stringRes = R.string.steps,
                        selected = false,
                        onClick = { onSelect(2) })
                    CategoryText(stringRes = R.string.distance,
                        selected = false,
                        onClick = { onSelect(3) })
                    CategoryText(stringRes = R.string.total_calories_burned,
                        selected = false,
                        onClick = { onSelect(4) })
                    CategoryText(stringRes = R.string.elevation_gained,
                        selected = false,
                        onClick = { onSelect(5) })
                    CategoryText(stringRes = R.string.exercise,
                        selected = false,
                        onClick = { onSelect(6) })
                    CategoryText(stringRes = R.string.floors_climbed,
                        selected = false,
                        onClick = { onSelect(7) })
                    CategoryText(stringRes = R.string.weight,
                        selected = false,
                        onClick = { onSelect(8) })
                }

                is MyDataState.Selected -> {
                    when (state.index) {
                        0 -> {
                            CategoryText(
                                stringRes = R.string.sleep,
                                selected = true,
                                onClick = onUnselect
                            )
                            DrawHealthConnectSubscreen(
                                viewModelData = sleepVMD,
                                onDisplayData = {
                                    items(sleepVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(widthSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) } }
                            )
                        }

                        1 -> {
                            CategoryText(
                                stringRes = R.string.heart_rate,
                                selected = true,
                                onClick = onUnselect
                            )
                            DrawHealthConnectSubscreen(
                                viewModelData = heartRateVMD,
                                onDisplayData = {
                                    items(heartRateVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(widthSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) } }
                            )
                        }

                        2 -> {
                            CategoryText(
                                stringRes = R.string.steps,
                                selected = true,
                                onClick = onUnselect
                            )
                            DrawHealthConnectSubscreen(
                                viewModelData = stepsVMD,
                                onDisplayData = {
                                    items(stepsVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(widthSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) } }
                            )
                        }

                        3 -> {
                            CategoryText(
                                stringRes = R.string.distance,
                                selected = true,
                                onClick = onUnselect
                            )
                            DrawHealthConnectSubscreen(
                                viewModelData = distanceVMD,
                                onDisplayData = {
                                    items(distanceVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(widthSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) } }
                            )
                        }

                        4 -> {
                            CategoryText(
                                stringRes = R.string.total_calories_burned,
                                selected = true,
                                onClick = onUnselect
                            )
                            DrawHealthConnectSubscreen(
                                viewModelData = totalCaloriesBurnedVMD,
                                onDisplayData = {
                                    items(totalCaloriesBurnedVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(widthSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) } }
                            )
                        }

                        5 -> {
                            CategoryText(
                                stringRes = R.string.elevation_gained,
                                selected = true,
                                onClick = onUnselect
                            )
                            DrawHealthConnectSubscreen(
                                viewModelData = elevationGainedVMD,
                                onDisplayData = {
                                    items(elevationGainedVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(widthSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) } }
                            )
                        }

                        6 -> {
                            CategoryText(
                                stringRes = R.string.exercise,
                                selected = true,
                                onClick = onUnselect
                            )
                            DrawHealthConnectSubscreen(
                                viewModelData = exerciseSessionVMD,
                                onDisplayData = {
                                    items(exerciseSessionVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(widthSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) } }
                            )
                        }

                        7 -> {
                            CategoryText(
                                stringRes = R.string.floors_climbed,
                                selected = true,
                                onClick = onUnselect
                            )
                            DrawHealthConnectSubscreen(
                                viewModelData = floorsClimbedVMD,
                                onDisplayData = {
                                    items(floorsClimbedVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(widthSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) } }
                            )
                        }

                        8 -> {
                            CategoryText(
                                stringRes = R.string.weight,
                                selected = true,
                                onClick = onUnselect
                            )
                            DrawHealthConnectSubscreen(
                                viewModelData = weightVMD,
                                onDisplayData = {
                                    items(weightVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(widthSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) } }
                            )
                        }
                    }
                }
            }
        }
    }
}
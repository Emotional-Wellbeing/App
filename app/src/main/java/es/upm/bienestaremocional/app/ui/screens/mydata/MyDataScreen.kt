package es.upm.bienestaremocional.app.ui.screens.mydata

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.records.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.healthconnect.types.SleepSessionData
import es.upm.bienestaremocional.app.ui.healthconnect.component.Display
import es.upm.bienestaremocional.app.ui.healthconnect.viewmodel.*
import es.upm.bienestaremocional.app.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.component.DrawHealthConnectSubscreen
import es.upm.bienestaremocional.core.ui.component.ViewModelData
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import kotlinx.coroutines.launch

@Destination
@Composable
fun MyDataScreen(
    navigator: DestinationsNavigator,
    windowSize: WindowSize,
    viewModel: MyDataViewModel = hiltViewModel(),
    sleepSessionViewModel: SleepSessionViewModel = hiltViewModel(),
    heartRateViewModel: HeartRateViewModel = hiltViewModel(),
    stepsViewModel: StepsViewModel = hiltViewModel(),
    basalMetabolicRateViewModel: BasalMetabolicRateViewModel = hiltViewModel(),
    bloodGlucoseViewModel: BloodGlucoseViewModel = hiltViewModel(),
    bloodPressureViewModel: BloodPressureViewModel = hiltViewModel(),
    distanceViewModel: DistanceViewModel = hiltViewModel(),
    oxygenSaturationViewModel: OxygenSaturationViewModel = hiltViewModel(),
    totalCaloriesBurnedViewModel: TotalCaloriesBurnedViewModel = hiltViewModel(),
    activeCaloriesBurnedViewModel: ActiveCaloriesBurnedViewModel = hiltViewModel(),
    bodyTemperatureViewModel: BodyTemperatureViewModel = hiltViewModel(),
    elevationGainedViewModel: ElevationGainedViewModel = hiltViewModel(),
    respiratoryRateViewModel: RespiratoryRateViewModel = hiltViewModel(),
    restingHeartRateViewModel: RestingHeartRateViewModel = hiltViewModel(),
    vo2MaxViewModel: Vo2MaxViewModel = hiltViewModel(),
)
{
    val snackbarHostState = remember {SnackbarHostState()}
    val state by viewModel.state.collectAsStateWithLifecycle()

    DrawMyDataScreen(
        navigator = navigator,
        windowSize = windowSize,
        snackbarHostState = snackbarHostState,
        state = state,
        sleepVMD = sleepSessionViewModel.getViewModelData(),
        heartRateVMD = heartRateViewModel.getViewModelData(),
        stepsVMD = stepsViewModel.getViewModelData(),
        basalMetabolicRateVMD = basalMetabolicRateViewModel.getViewModelData(),
        bloodGlucoseVMD = bloodGlucoseViewModel.getViewModelData(),
        bloodPressureVMD = bloodPressureViewModel.getViewModelData(),
        distanceVMD = distanceViewModel.getViewModelData(),
        oxygenSaturationVMD = oxygenSaturationViewModel.getViewModelData(),
        totalCaloriesBurnedVMD = totalCaloriesBurnedViewModel.getViewModelData(),
        activeCaloriesBurnedVMD = activeCaloriesBurnedViewModel.getViewModelData(),
        bodyTemperatureVMD = bodyTemperatureViewModel.getViewModelData(),
        elevationGainedVMD = elevationGainedViewModel.getViewModelData(),
        respiratoryRateVMD = respiratoryRateViewModel.getViewModelData(),
        restingHeartRateVMD = restingHeartRateViewModel.getViewModelData(),
        vo2MaxVMD = vo2MaxViewModel.getViewModelData(),
        onSelect = {index -> viewModel.onSelect(index)},
        onUnselect = {viewModel.onUnselect()},
        onError = {exception -> viewModel.onError(snackbarHostState,exception)})
}

@Composable
private fun CategoryText(@StringRes stringRes: Int,
                         selected: Boolean,
                         onClick: () -> Unit)
{
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
            color = MaterialTheme.colorScheme.primary)
        IconButton(onClick = onClick)
        {
            val icon = if (selected)
                Icons.Default.KeyboardArrowDown
            else
                Icons.Default.KeyboardArrowRight
            Icon(icon,  contentDescription = "Expandir")
        }
    }
}

@Composable
private fun DrawMyDataScreen(navigator: DestinationsNavigator,
                             windowSize: WindowSize,
                             snackbarHostState : SnackbarHostState,
                             state: MyDataState,
                             sleepVMD: ViewModelData<SleepSessionData>,
                             heartRateVMD: ViewModelData<HeartRateRecord>,
                             stepsVMD: ViewModelData<StepsRecord>,
                             basalMetabolicRateVMD: ViewModelData<BasalMetabolicRateRecord>,
                             bloodGlucoseVMD: ViewModelData<BloodGlucoseRecord>,
                             bloodPressureVMD: ViewModelData<BloodPressureRecord>,
                             distanceVMD: ViewModelData<DistanceRecord>,
                             oxygenSaturationVMD: ViewModelData<OxygenSaturationRecord>,
                             totalCaloriesBurnedVMD: ViewModelData<TotalCaloriesBurnedRecord>,
                             activeCaloriesBurnedVMD: ViewModelData<ActiveCaloriesBurnedRecord>,
                             bodyTemperatureVMD: ViewModelData<BodyTemperatureRecord>,
                             elevationGainedVMD: ViewModelData<ElevationGainedRecord>,
                             respiratoryRateVMD: ViewModelData<RespiratoryRateRecord>,
                             restingHeartRateVMD: ViewModelData<RestingHeartRateRecord>,
                             vo2MaxVMD: ViewModelData<Vo2MaxRecord>,
                             onSelect: (Int) -> Unit,
                             onUnselect : () -> Unit,
                             onError: suspend (Throwable?) -> Unit = {})
{
    val coroutineScope = rememberCoroutineScope()
    
    AppBasicScreen(navigator = navigator,
        entrySelected = BottomBarDestination.SettingsScreen,
        label = R.string.my_data_label,
        snackbarHostState = snackbarHostState
    )
    {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            when(state)
            {
                is MyDataState.NoSelection -> {
                    CategoryText(stringRes = R.string.sleep,
                        selected = false,
                        onClick = {onSelect(0)})
                    CategoryText(stringRes = R.string.heart_rate,
                        selected = false,
                        onClick = {onSelect(1)})
                    CategoryText(stringRes = R.string.steps,
                        selected = false,
                        onClick = {onSelect(2)})
                    CategoryText(stringRes = R.string.bmr,
                        selected = false,
                        onClick = {onSelect(3)})
                    CategoryText(stringRes = R.string.blood_glucose,
                        selected = false,
                        onClick = {onSelect(4)})
                    CategoryText(stringRes = R.string.blood_pressure,
                        selected = false,
                        onClick = {onSelect(5)})
                    CategoryText(stringRes = R.string.distance,
                        selected = false,
                        onClick = {onSelect(6)})
                    CategoryText(stringRes = R.string.oxygen_saturation,
                        selected = false,
                        onClick = {onSelect(7)})
                    CategoryText(stringRes = R.string.total_calories_burned,
                        selected = false,
                        onClick = {onSelect(8)})
                    CategoryText(stringRes = R.string.active_calories_burned,
                        selected = false,
                        onClick = {onSelect(9)})
                    CategoryText(stringRes = R.string.body_temperature,
                        selected = false,
                        onClick = {onSelect(10)})
                    CategoryText(stringRes = R.string.elevation_gained,
                        selected = false,
                        onClick = {onSelect(11)})
                    CategoryText(stringRes = R.string.respiratory_rate,
                        selected = false,
                        onClick = {onSelect(12)})
                    CategoryText(stringRes = R.string.resting_heart_rate,
                        selected = false,
                        onClick = {onSelect(13)})
                    CategoryText(stringRes = R.string.vo2_max,
                        selected = false,
                        onClick = {onSelect(14)})
                }
                is MyDataState.Selected ->
                {
                    when(state.index)
                    {
                        0 -> {
                            CategoryText(stringRes = R.string.sleep,
                                selected = true,
                                onClick = onUnselect)
                            DrawHealthConnectSubscreen(
                                viewModelData = sleepVMD,
                                onDisplayData = {
                                    items(sleepVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(windowSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) }}
                            )
                        }
                        1 -> {
                            CategoryText(stringRes = R.string.heart_rate,
                                selected = true,
                                onClick = onUnselect)
                            DrawHealthConnectSubscreen(
                                viewModelData = heartRateVMD,
                                onDisplayData = {
                                    items(heartRateVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(windowSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) }}
                            )
                        }
                        2 -> {
                            CategoryText(stringRes = R.string.steps,
                                selected = true,
                                onClick = onUnselect)
                            DrawHealthConnectSubscreen(
                                viewModelData = stepsVMD,
                                onDisplayData = {
                                    items(stepsVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(windowSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) }}
                            )
                        }
                        3 -> {
                            CategoryText(stringRes = R.string.bmr,
                                selected = true,
                                onClick = onUnselect)
                            DrawHealthConnectSubscreen(
                                viewModelData = basalMetabolicRateVMD,
                                onDisplayData = {
                                    items(basalMetabolicRateVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(windowSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) }}
                            )
                        }
                        4 -> {
                            CategoryText(stringRes = R.string.blood_glucose,
                                selected = true,
                                onClick = onUnselect)
                            DrawHealthConnectSubscreen(
                                viewModelData = bloodGlucoseVMD,
                                onDisplayData = {
                                    items(bloodGlucoseVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(windowSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) }}
                            )
                        }
                        5 -> {
                            CategoryText(stringRes = R.string.blood_pressure,
                                selected = true,
                                onClick = onUnselect)
                            DrawHealthConnectSubscreen(
                                viewModelData = bloodPressureVMD,
                                onDisplayData = {
                                    items(bloodPressureVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(windowSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) }}
                            )
                        }
                        6 -> {
                            CategoryText(stringRes = R.string.distance,
                                selected = true,
                                onClick = onUnselect)
                            DrawHealthConnectSubscreen(
                                viewModelData = distanceVMD,
                                onDisplayData = {
                                    items(distanceVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(windowSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) }}
                            )
                        }
                        7 -> {
                            CategoryText(stringRes = R.string.oxygen_saturation,
                                selected = true,
                                onClick = onUnselect)
                            DrawHealthConnectSubscreen(
                                viewModelData = oxygenSaturationVMD,
                                onDisplayData = {
                                    items(oxygenSaturationVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(windowSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) }}
                            )
                        }
                        8 -> {
                            CategoryText(stringRes = R.string.total_calories_burned,
                                selected = true,
                                onClick = onUnselect)
                            DrawHealthConnectSubscreen(
                                viewModelData = totalCaloriesBurnedVMD,
                                onDisplayData = {
                                    items(totalCaloriesBurnedVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(windowSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) }}
                            )
                        }
                        9 -> {
                            CategoryText(stringRes = R.string.active_calories_burned,
                                selected = true,
                                onClick = onUnselect)
                            DrawHealthConnectSubscreen(
                                viewModelData = activeCaloriesBurnedVMD,
                                onDisplayData = {
                                    items(activeCaloriesBurnedVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(windowSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) }}
                            )
                        }
                        10 -> {
                            CategoryText(stringRes = R.string.body_temperature,
                                selected = true,
                                onClick = onUnselect)
                            DrawHealthConnectSubscreen(
                                viewModelData = bodyTemperatureVMD,
                                onDisplayData = {
                                    items(bodyTemperatureVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(windowSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) }}
                            )
                        }
                        11 -> {
                            CategoryText(stringRes = R.string.elevation_gained,
                                selected = true,
                                onClick = onUnselect)
                            DrawHealthConnectSubscreen(
                                viewModelData = elevationGainedVMD,
                                onDisplayData = {
                                    items(elevationGainedVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(windowSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) }}
                            )
                        }
                        12 -> {
                            CategoryText(stringRes = R.string.respiratory_rate,
                                selected = true,
                                onClick = onUnselect)
                            DrawHealthConnectSubscreen(
                                viewModelData = respiratoryRateVMD,
                                onDisplayData = {
                                    items(respiratoryRateVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(windowSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) }}
                            )
                        }
                        13 -> {
                            CategoryText(stringRes = R.string.resting_heart_rate,
                                selected = true,
                                onClick = onUnselect)
                            DrawHealthConnectSubscreen(
                                viewModelData = restingHeartRateVMD,
                                onDisplayData = {
                                    items(restingHeartRateVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(windowSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) }}
                            )
                        }
                        14 -> {
                            CategoryText(stringRes = R.string.vo2_max,
                                selected = true,
                                onClick = onUnselect)
                            DrawHealthConnectSubscreen(
                                viewModelData = vo2MaxVMD,
                                onDisplayData = {
                                    items(vo2MaxVMD.data)
                                    {
                                        Spacer(Modifier.height(16.dp))
                                        it.Display(windowSize)
                                    }
                                },
                                onError = { coroutineScope.launch { onError(it) }}
                            )
                        }
                    }
                }
            }
        }
    }
}
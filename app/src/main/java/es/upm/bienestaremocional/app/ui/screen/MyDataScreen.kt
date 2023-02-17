package es.upm.bienestaremocional.app.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.records.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.healthconnect.types.SleepSessionData
import es.upm.bienestaremocional.app.ui.healthconnect.component.Display
import es.upm.bienestaremocional.app.ui.healthconnect.viewmodel.*
import es.upm.bienestaremocional.app.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.app.ui.viewmodel.MyDataViewModel
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.component.DrawHealthConnectSubscreen
import es.upm.bienestaremocional.core.ui.component.ViewModelData
import es.upm.bienestaremocional.core.ui.responsive.WindowSize

internal class ExpanderElements(size: Int)
{
    private val expanders: Array<MutableState<Boolean>> = Array(size) { mutableStateOf(false) }

    fun select(index: Int)
    {
        if (index in 0 until this.expanders.size)
            this.expanders[index].value = true
    }

    fun unselect(index: Int)
    {
        if (index in 0 until this.expanders.size)
            this.expanders[index].value = false
    }

    fun allAreUnselected(): Boolean = this.expanders.all { !it.value }

    fun get(index: Int): MutableState<Boolean>?
    {
        return if (index in 0 until this.expanders.size)
            this.expanders[index]
        else
            null
    }

}

private fun clickable(index: Int, expanderElements: ExpanderElements)
{
    expanderElements.get(index)?.let {
        if (it.value)
        {
            expanderElements.unselect(index)
        }
        else
        {
            expanderElements.select(index)
        }
    }

}

@Composable
private fun CategoryText(index: Int, @StringRes stringRes: Int, expanderElements: ExpanderElements)
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
        IconButton(onClick = { clickable(index, expanderElements) })
        {
            val icon = if(expanderElements.get(index)?.value == true)
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
                             onError: (Throwable?) -> Unit = {})
{
    val expanderElements = remember {ExpanderElements(15) }

    AppBasicScreen(navigator = navigator,
        entrySelected = BottomBarDestination.SettingsScreen,
        label = R.string.my_data_label)
    {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            if (expanderElements.allAreUnselected() || expanderElements.get(0)?.value == true)
            {
                item {
                    CategoryText(
                        index = 0,
                        stringRes = R.string.sleep,
                        expanderElements = expanderElements
                    )

                    if (expanderElements.get(0)?.value == true) {
                        DrawHealthConnectSubscreen(
                            viewModelData = sleepVMD,
                            lazyListScope = this@LazyColumn,
                            onDisplayData = {
                                this@LazyColumn.items(sleepVMD.data)
                                {
                                    Spacer(Modifier.height(16.dp))
                                    it.Display(windowSize)
                                }
                            },
                            onError = onError
                        )
                    }
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(1)?.value == true)
            {
                item {
                    CategoryText(
                        index = 1,
                        stringRes = R.string.heart_rate,
                        expanderElements = expanderElements
                    )
                    if (expanderElements.get(1)?.value == true) {
                        DrawHealthConnectSubscreen(
                            viewModelData = heartRateVMD,
                            lazyListScope = this@LazyColumn,
                            onDisplayData = {
                                this@LazyColumn.items(heartRateVMD.data)
                                {
                                    Spacer(Modifier.height(16.dp))
                                    it.Display(windowSize)
                                }
                            },
                            onError = onError
                        )
                    }
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(2)?.value == true)
            {
                item {
                    CategoryText(
                        index = 2,
                        stringRes = R.string.steps,
                        expanderElements = expanderElements
                    )
                    if (expanderElements.get(2)?.value == true) {
                        DrawHealthConnectSubscreen(
                            viewModelData = stepsVMD,
                            lazyListScope = this@LazyColumn,
                            onDisplayData = {
                                this@LazyColumn.items(stepsVMD.data)
                                {
                                    Spacer(Modifier.height(16.dp))
                                    it.Display(windowSize)
                                }
                            },
                            onError = onError
                        )
                    }
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(3)?.value == true)
            {
                item {
                    CategoryText(
                        index = 3,
                        stringRes = R.string.bmr,
                        expanderElements = expanderElements
                    )
                    if (expanderElements.get(3)?.value == true) {
                        DrawHealthConnectSubscreen(
                            viewModelData = basalMetabolicRateVMD,
                            lazyListScope = this@LazyColumn,
                            onDisplayData = {
                                this@LazyColumn.items(basalMetabolicRateVMD.data)
                                {
                                    Spacer(Modifier.height(16.dp))
                                    it.Display(windowSize)
                                }
                            },
                            onError = onError
                        )
                    }
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(4)?.value == true)
            {
                item {
                    CategoryText(
                        index = 4,
                        stringRes = R.string.blood_glucose,
                        expanderElements = expanderElements
                    )
                    if (expanderElements.get(4)?.value == true) {
                        DrawHealthConnectSubscreen(
                            viewModelData = bloodGlucoseVMD,
                            lazyListScope = this@LazyColumn,
                            onDisplayData = {
                                this@LazyColumn.items(bloodGlucoseVMD.data)
                                {
                                    Spacer(Modifier.height(16.dp))
                                    it.Display(windowSize)
                                }
                            },
                            onError = onError
                        )
                    }
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(5)?.value == true)
            {
                item {
                    CategoryText(
                        index = 5,
                        stringRes = R.string.blood_pressure,
                        expanderElements = expanderElements
                    )
                    if (expanderElements.get(5)?.value == true) {
                        DrawHealthConnectSubscreen(
                            viewModelData = bloodPressureVMD,
                            lazyListScope = this@LazyColumn,
                            onDisplayData = {
                                this@LazyColumn.items(bloodPressureVMD.data)
                                {
                                    Spacer(Modifier.height(16.dp))
                                    it.Display(windowSize)
                                }
                            },
                            onError = onError
                        )
                    }
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(6)?.value == true)
            {
                item {
                    CategoryText(
                        index = 6,
                        stringRes = R.string.distance,
                        expanderElements = expanderElements
                    )
                    if (expanderElements.get(6)?.value == true) {
                        DrawHealthConnectSubscreen(
                            viewModelData = distanceVMD,
                            lazyListScope = this@LazyColumn,
                            onDisplayData = {
                                this@LazyColumn.items(distanceVMD.data)
                                {
                                    Spacer(Modifier.height(16.dp))
                                    it.Display(windowSize)
                                }
                            },
                            onError = onError
                        )
                    }
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(7)?.value == true)
            {
                item {
                    CategoryText(
                        index = 7,
                        stringRes = R.string.oxygen_saturation,
                        expanderElements = expanderElements
                    )
                    if (expanderElements.get(7)?.value == true) {
                        DrawHealthConnectSubscreen(
                            viewModelData = oxygenSaturationVMD,
                            lazyListScope = this@LazyColumn,
                            onDisplayData = {
                                this@LazyColumn.items(oxygenSaturationVMD.data)
                                {
                                    Spacer(Modifier.height(16.dp))
                                    it.Display(windowSize)
                                }
                            },
                            onError = onError
                        )
                    }
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(8)?.value == true)
            {
                item {
                    CategoryText(
                        index = 8,
                        stringRes = R.string.total_calories_burned,
                        expanderElements = expanderElements
                    )
                    if (expanderElements.get(8)?.value == true) {
                        DrawHealthConnectSubscreen(
                            viewModelData = totalCaloriesBurnedVMD,
                            lazyListScope = this@LazyColumn,
                            onDisplayData = {
                                this@LazyColumn.items(totalCaloriesBurnedVMD.data)
                                {
                                    Spacer(Modifier.height(16.dp))
                                    it.Display(windowSize)
                                }
                            },
                            onError = onError
                        )
                    }
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(9)?.value == true)
            {
                item {
                    CategoryText(
                        index = 9,
                        stringRes = R.string.active_calories_burned,
                        expanderElements = expanderElements
                    )
                    if (expanderElements.get(9)?.value == true) {
                        DrawHealthConnectSubscreen(
                            viewModelData = activeCaloriesBurnedVMD,
                            lazyListScope = this@LazyColumn,
                            onDisplayData = {
                                this@LazyColumn.items(activeCaloriesBurnedVMD.data)
                                {
                                    Spacer(Modifier.height(16.dp))
                                    it.Display(windowSize)
                                }
                            },
                            onError = onError
                        )
                    }
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(10)?.value == true)
            {
                item {
                    CategoryText(
                        index = 10,
                        stringRes = R.string.body_temperature,
                        expanderElements = expanderElements
                    )
                    if (expanderElements.get(10)?.value == true) {
                        DrawHealthConnectSubscreen(
                            viewModelData = bodyTemperatureVMD,
                            lazyListScope = this@LazyColumn,
                            onDisplayData = {
                                this@LazyColumn.items(bodyTemperatureVMD.data)
                                {
                                    Spacer(Modifier.height(16.dp))
                                    it.Display(windowSize)
                                }
                            },
                            onError = onError
                        )
                    }
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(11)?.value == true)
            {
                item {
                    CategoryText(
                        index = 11,
                        stringRes = R.string.elevation_gained,
                        expanderElements = expanderElements
                    )
                    if (expanderElements.get(11)?.value == true) {
                        DrawHealthConnectSubscreen(
                            viewModelData = elevationGainedVMD,
                            lazyListScope = this@LazyColumn,
                            onDisplayData = {
                                this@LazyColumn.items(elevationGainedVMD.data)
                                {
                                    Spacer(Modifier.height(16.dp))
                                    it.Display(windowSize)
                                }
                            },
                            onError = onError
                        )
                    }
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(12)?.value == true)
            {
                item {
                    CategoryText(
                        index = 12,
                        stringRes = R.string.respiratory_rate,
                        expanderElements = expanderElements
                    )
                    if (expanderElements.get(12)?.value == true) {
                        DrawHealthConnectSubscreen(
                            viewModelData = respiratoryRateVMD,
                            lazyListScope = this@LazyColumn,
                            onDisplayData = {
                                this@LazyColumn.items(respiratoryRateVMD.data)
                                {
                                    Spacer(Modifier.height(16.dp))
                                    it.Display(windowSize)
                                }
                            },
                            onError = onError
                        )
                    }
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(13)?.value == true)
            {
                item {
                    CategoryText(
                        index = 13,
                        stringRes = R.string.resting_heart_rate,
                        expanderElements = expanderElements
                    )
                    if (expanderElements.get(13)?.value == true) {
                        DrawHealthConnectSubscreen(
                            viewModelData = restingHeartRateVMD,
                            lazyListScope = this@LazyColumn,
                            onDisplayData = {
                                this@LazyColumn.items(restingHeartRateVMD.data)
                                {
                                    Spacer(Modifier.height(16.dp))
                                    it.Display(windowSize)
                                }
                            },
                            onError = onError
                        )
                    }
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(14)?.value == true)
            {
                item {
                    CategoryText(
                        index = 14,
                        stringRes = R.string.vo2_max,
                        expanderElements = expanderElements
                    )
                    if (expanderElements.get(14)?.value == true) {
                        DrawHealthConnectSubscreen(
                            viewModelData = vo2MaxVMD,
                            lazyListScope = this@LazyColumn,
                            onDisplayData = {
                                this@LazyColumn.items(vo2MaxVMD.data)
                                {
                                    Spacer(Modifier.height(16.dp))
                                    it.Display(windowSize)
                                }
                            },
                            onError = onError
                        )
                    }
                }
            }
        }
    }
}

@Destination
@Composable
fun MyDataScreen(navigator: DestinationsNavigator,
                 windowSize: WindowSize,
                 viewModel: MyDataViewModel,
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
    DrawMyDataScreen(
        navigator = navigator,
        windowSize = windowSize,
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
        onError = {exception -> viewModel.onError(exception)})
}
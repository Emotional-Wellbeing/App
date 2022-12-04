package es.upm.bienestaremocional.app.ui.healthconnect

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.healthconnect.types.SleepSessionData
import es.upm.bienestaremocional.app.ui.healthconnect.component.Display
import es.upm.bienestaremocional.app.ui.healthconnect.viewmodel.*
import es.upm.bienestaremocional.core.ui.component.DrawHealthConnectScreen
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
private fun CategoryText(index: Int, stringRes: Int, expanderElements: ExpanderElements)
{
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
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
private fun DrawDebugScreen(sleepVMD: ViewModelData<SleepSessionData>,
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
                            windowSize: WindowSize,
                            onError: (Throwable?) -> Unit = {})
{
    val expanderElements = remember {ExpanderElements(14) }

    Surface(modifier = Modifier.fillMaxSize())
    {
        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(8.dp))
        {
            if (expanderElements.allAreUnselected() || expanderElements.get(0)?.value == true)
            {
                CategoryText(index = 0,
                    stringRes = R.string.sleep,
                    expanderElements = expanderElements)
                if (expanderElements.get(0)?.value == true)
                {
                    DrawHealthConnectScreen(viewModelData = sleepVMD,
                        onDisplayData = {
                            sleepVMD.data.forEach {
                                item {
                                    it.Display(windowSize)
                                    Spacer(Modifier.height(16.dp))
                                }
                            }
                        },
                        onError = onError)
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(1)?.value == true)
            {
                CategoryText(index = 1,
                    stringRes = R.string.heart_rate,
                    expanderElements = expanderElements)
                if (expanderElements.get(1)?.value == true)
                {
                    DrawHealthConnectScreen(viewModelData = heartRateVMD,
                        onDisplayData = {
                            heartRateVMD.data.forEach {
                                item {
                                    it.Display(windowSize)
                                    Spacer(Modifier.height(16.dp))
                                }
                            }
                        },
                        onError = onError)
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(2)?.value == true)
            {
                CategoryText(index = 2,
                    stringRes = R.string.steps,
                    expanderElements = expanderElements)
                if (expanderElements.get(2)?.value == true)
                {
                    DrawHealthConnectScreen(
                        viewModelData = stepsVMD,
                        onDisplayData = {
                            stepsVMD.data.forEach {
                                item {
                                    it.Display(windowSize)
                                    Spacer(Modifier.height(16.dp))
                                }
                            }
                        },
                        onError = onError
                    )
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(3)?.value == true)
            {
                CategoryText(index = 3,
                    stringRes = R.string.bmr,
                    expanderElements = expanderElements)
                if (expanderElements.get(3)?.value == true)
                {
                    DrawHealthConnectScreen(viewModelData = basalMetabolicRateVMD,
                        onDisplayData = {
                            basalMetabolicRateVMD.data.forEach {
                                item {
                                    it.Display(windowSize)
                                    Spacer(Modifier.height(16.dp))
                                }
                            }
                        },
                        onError = onError)
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(4)?.value == true)
            {
                CategoryText(index = 4,
                    stringRes = R.string.blood_glucose,
                    expanderElements = expanderElements)
                if (expanderElements.get(4)?.value == true)
                {
                    DrawHealthConnectScreen(viewModelData = bloodGlucoseVMD,
                        onDisplayData = {
                            bloodGlucoseVMD.data.forEach {
                                item {
                                    it.Display(windowSize)
                                    Spacer(Modifier.height(16.dp))
                                }
                            }
                        },
                        onError = onError)
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(5)?.value == true)
            {
                CategoryText(index = 5,
                    stringRes = R.string.blood_pressure,
                    expanderElements = expanderElements)
                if (expanderElements.get(5)?.value == true)
                {
                    DrawHealthConnectScreen(viewModelData = bloodPressureVMD,
                        onDisplayData = {
                            bloodPressureVMD.data.forEach {
                                item {
                                    it.Display(windowSize)
                                    Spacer(Modifier.height(16.dp))
                                }
                            }
                        },
                        onError = onError)
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(6)?.value == true)
            {
                CategoryText(index = 6,
                    stringRes = R.string.distance,
                    expanderElements = expanderElements)
                if (expanderElements.get(6)?.value == true)
                {
                    DrawHealthConnectScreen(viewModelData = distanceVMD,
                        onDisplayData = {
                            distanceVMD.data.forEach {
                                item {
                                    it.Display(windowSize)
                                    Spacer(Modifier.height(16.dp))
                                }
                            }
                        },
                        onError = onError)
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(7)?.value == true)
            {
                CategoryText(index = 7,
                    stringRes = R.string.oxygen_saturation,
                    expanderElements = expanderElements)
                if (expanderElements.get(7)?.value == true)
                {
                    DrawHealthConnectScreen(viewModelData = oxygenSaturationVMD,
                        onDisplayData = {
                            oxygenSaturationVMD.data.forEach {
                                item {
                                    it.Display(windowSize)
                                    Spacer(Modifier.height(16.dp))
                                }
                            }
                        },
                        onError = onError)
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(8)?.value == true)
            {
                CategoryText(index = 8,
                    stringRes = R.string.total_calories_burned,
                    expanderElements = expanderElements)
                if (expanderElements.get(8)?.value == true)
                {
                    DrawHealthConnectScreen(viewModelData = totalCaloriesBurnedVMD,
                        onDisplayData = {
                            totalCaloriesBurnedVMD.data.forEach {
                                item {
                                    it.Display(windowSize)
                                    Spacer(Modifier.height(16.dp))
                                }
                            }
                        },
                        onError = onError)
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(9)?.value == true)
            {
                CategoryText(index = 9,
                    stringRes = R.string.active_calories_burned,
                    expanderElements = expanderElements)
                if (expanderElements.get(9)?.value == true)
                {
                    DrawHealthConnectScreen(viewModelData = activeCaloriesBurnedVMD,
                        onDisplayData = {
                            activeCaloriesBurnedVMD.data.forEach {
                                item {
                                    it.Display(windowSize)
                                    Spacer(Modifier.height(16.dp))
                                }
                            }
                        },
                        onError = onError)
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(10)?.value == true)
            {
                CategoryText(index = 10,
                    stringRes = R.string.body_temperature,
                    expanderElements = expanderElements)
                if (expanderElements.get(10)?.value == true)
                {
                    DrawHealthConnectScreen(viewModelData = bodyTemperatureVMD,
                        onDisplayData = {
                            bodyTemperatureVMD.data.forEach {
                                item {
                                    it.Display(windowSize)
                                    Spacer(Modifier.height(16.dp))
                                }
                            }
                        },
                        onError = onError)
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(11)?.value == true)
            {
                CategoryText(index = 11,
                    stringRes = R.string.elevation_gained,
                    expanderElements = expanderElements)
                if (expanderElements.get(11)?.value == true)
                {
                    DrawHealthConnectScreen(viewModelData = elevationGainedVMD,
                        onDisplayData = {
                            elevationGainedVMD.data.forEach {
                                item {
                                    it.Display(windowSize)
                                    Spacer(Modifier.height(16.dp))
                                }
                            }
                        },
                        onError = onError)
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(12)?.value == true)
            {
                CategoryText(index = 12,
                    stringRes = R.string.respiratory_rate,
                    expanderElements = expanderElements)
                if (expanderElements.get(12)?.value == true)
                {
                    DrawHealthConnectScreen(viewModelData = respiratoryRateVMD,
                        onDisplayData = {
                            respiratoryRateVMD.data.forEach {
                                item {
                                    it.Display(windowSize)
                                    Spacer(Modifier.height(16.dp))
                                }
                            }
                        },
                        onError = onError)
                }
            }

            if (expanderElements.allAreUnselected() || expanderElements.get(13)?.value == true)
            {
                CategoryText(index = 13,
                    stringRes = R.string.resting_heart_rate,
                    expanderElements = expanderElements)
                if (expanderElements.get(13)?.value == true)
                {
                    DrawHealthConnectScreen(viewModelData = restingHeartRateVMD,
                        onDisplayData = {
                            restingHeartRateVMD.data.forEach {
                                item {
                                    it.Display(windowSize)
                                    Spacer(Modifier.height(16.dp))
                                }
                            }
                        },
                        onError = onError)
                }
            }
        }
    }
}

@Composable
fun DebugScreen(windowSize: WindowSize,
                onError: (Throwable?) -> Unit = {})
{
    val sleepSessionViewModel: SleepSessionViewModel =
        viewModel(factory = SleepSessionViewModel.Factory)
    val heartRateViewModel: HeartRateViewModel =
        viewModel(factory = HeartRateViewModel.Factory)
    val stepsViewModel: StepsViewModel =
        viewModel(factory = StepsViewModel.Factory)
    val basalMetabolicRateViewModel: BasalMetabolicRateViewModel =
        viewModel(factory = BasalMetabolicRateViewModel.Factory)
    val bloodGlucoseViewModel: BloodGlucoseViewModel =
        viewModel(factory = BloodGlucoseViewModel.Factory)
    val bloodPressureViewModel: BloodPressureViewModel =
        viewModel(factory = BloodPressureViewModel.Factory)
    val distanceViewModel: DistanceViewModel =
        viewModel(factory = DistanceViewModel.Factory)
    val oxygenSaturationViewModel: OxygenSaturationViewModel =
        viewModel(factory = OxygenSaturationViewModel.Factory)
    val totalCaloriesBurnedViewModel: TotalCaloriesBurnedViewModel =
        viewModel(factory = TotalCaloriesBurnedViewModel.Factory)
    val activeCaloriesBurnedViewModel: ActiveCaloriesBurnedViewModel =
        viewModel(factory = ActiveCaloriesBurnedViewModel.Factory)
    val bodyTemperatureViewModel: BodyTemperatureViewModel =
        viewModel(factory = BodyTemperatureViewModel.Factory)
    val elevationGainedViewModel: ElevationGainedViewModel =
        viewModel(factory = ElevationGainedViewModel.Factory)
    val respiratoryRateViewModel: RespiratoryRateViewModel =
        viewModel(factory = RespiratoryRateViewModel.Factory)
    val restingHeartRateViewModel: RestingHeartRateViewModel =
        viewModel(factory = RestingHeartRateViewModel.Factory)

    DrawDebugScreen(
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
        windowSize = windowSize,
        onError = onError)
}
package es.upm.bienestaremocional.app.ui.healthconnect

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

@Suppress("UNCHECKED_CAST")
@Composable
private fun DrawDebugScreen(sleepViewModelData: ViewModelData,
                            heartRateViewModelData: ViewModelData,
                            stepsViewModelData: ViewModelData,
                            bmrViewModelData: ViewModelData,
                            bloodGlucoseViewModelData: ViewModelData,
                            bloodPressureViewModelData: ViewModelData,
                            distanceViewModelData: ViewModelData,
                            oxygenSaturationViewModelData: ViewModelData,
                            totalCaloriesBurnedViewModelData: ViewModelData,
                            activeCaloriesBurnedViewModelData: ViewModelData,
                            bodyTemperatureViewModelData: ViewModelData,
                            elevationGainedViewModelData: ViewModelData,
                            respiratoryRateViewModelData: ViewModelData,
                            restingHeartRateViewModelData: ViewModelData,
                            windowSize: WindowSize,
                            onError: (Throwable?) -> Unit = {})
{
    val expanderElements = remember {ExpanderElements(14) }

    Surface(modifier = Modifier.fillMaxSize())
    {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp))
        {
            if (expanderElements.allAreUnselected() || expanderElements.get(0)?.value == true)
            {
                CategoryText(index = 0,
                    stringRes = R.string.sleep,
                    expanderElements = expanderElements)
                if (expanderElements.get(0)?.value == true)
                {
                    DrawHealthConnectScreen(viewModelData = sleepViewModelData,
                        onDisplayData = {
                            val data = sleepViewModelData.data as List<SleepSessionData>
                            data.forEach {
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
                    DrawHealthConnectScreen(viewModelData = heartRateViewModelData,
                        onDisplayData = {
                            val data = heartRateViewModelData.data as List<HeartRateRecord>
                            data.forEach {
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
                        viewModelData = stepsViewModelData,
                        onDisplayData = {
                            val data = stepsViewModelData.data as List<StepsRecord>
                            data.forEach {
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
                    DrawHealthConnectScreen(viewModelData = bmrViewModelData,
                        onDisplayData = {
                            val data = bmrViewModelData.data as List<BasalMetabolicRateRecord>
                            data.forEach {
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
                    DrawHealthConnectScreen(viewModelData = bloodGlucoseViewModelData,
                        onDisplayData = {
                            val data = bloodGlucoseViewModelData.data
                                    as List<BloodGlucoseRecord>
                            data.forEach {
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
                    DrawHealthConnectScreen(viewModelData = bloodPressureViewModelData,
                        onDisplayData = {
                            val data = bloodPressureViewModelData.data
                                    as List<BloodPressureRecord>
                            data.forEach {
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
                    DrawHealthConnectScreen(viewModelData = distanceViewModelData,
                        onDisplayData = {
                            val data = distanceViewModelData.data
                                    as List<DistanceRecord>
                            data.forEach {
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
                    DrawHealthConnectScreen(viewModelData = oxygenSaturationViewModelData,
                        onDisplayData = {
                            val data = oxygenSaturationViewModelData.data
                                    as List<OxygenSaturationRecord>
                            data.forEach {
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
                    DrawHealthConnectScreen(viewModelData = totalCaloriesBurnedViewModelData,
                        onDisplayData = {
                            val data = totalCaloriesBurnedViewModelData.data
                                    as List<TotalCaloriesBurnedRecord>
                            data.forEach {
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
                    DrawHealthConnectScreen(viewModelData = activeCaloriesBurnedViewModelData,
                        onDisplayData = {
                            val data = activeCaloriesBurnedViewModelData.data
                                    as List<ActiveCaloriesBurnedRecord>
                            data.forEach {
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
                    DrawHealthConnectScreen(viewModelData = bodyTemperatureViewModelData,
                        onDisplayData = {
                            val data = bodyTemperatureViewModelData.data
                                    as List<BodyTemperatureRecord>
                            data.forEach {
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
                    DrawHealthConnectScreen(viewModelData = elevationGainedViewModelData,
                        onDisplayData = {
                            val data = elevationGainedViewModelData.data
                                    as List<ElevationGainedRecord>
                            data.forEach {
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
                    DrawHealthConnectScreen(viewModelData = respiratoryRateViewModelData,
                        onDisplayData = {
                            val data = respiratoryRateViewModelData.data
                                    as List<RespiratoryRateRecord>
                            data.forEach {
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
                    DrawHealthConnectScreen(viewModelData = restingHeartRateViewModelData,
                        onDisplayData = {
                            val data = restingHeartRateViewModelData.data
                                    as List<RestingHeartRateRecord>
                            data.forEach {
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
        sleepViewModelData = sleepSessionViewModel.getViewModelData(),
        heartRateViewModelData = heartRateViewModel.getViewModelData(),
        stepsViewModelData = stepsViewModel.getViewModelData(),
        bmrViewModelData = basalMetabolicRateViewModel.getViewModelData(),
        bloodGlucoseViewModelData = bloodGlucoseViewModel.getViewModelData(),
        bloodPressureViewModelData = bloodPressureViewModel.getViewModelData(),
        distanceViewModelData = distanceViewModel.getViewModelData(),
        oxygenSaturationViewModelData = oxygenSaturationViewModel.getViewModelData(),
        totalCaloriesBurnedViewModelData = totalCaloriesBurnedViewModel.getViewModelData(),
        activeCaloriesBurnedViewModelData = activeCaloriesBurnedViewModel.getViewModelData(),
        bodyTemperatureViewModelData = bodyTemperatureViewModel.getViewModelData(),
        elevationGainedViewModelData = elevationGainedViewModel.getViewModelData(),
        respiratoryRateViewModelData = respiratoryRateViewModel.getViewModelData(),
        restingHeartRateViewModelData = restingHeartRateViewModel.getViewModelData(),
        windowSize = windowSize,
        onError = onError)
}
package es.upm.bienestaremocional.app.ui.healthconnect

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.records.BasalMetabolicRateRecord
import androidx.health.connect.client.records.BloodGlucoseRecord
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.StepsRecord
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
            expanderElements.unselect(index)
        else
            expanderElements.select(index)
    }

}

@Composable
private fun CategoryText(index: Int, stringRes: Int, expanderElements: ExpanderElements)
{
    Text(modifier = Modifier
        .fillMaxWidth()
        .clickable { clickable(index, expanderElements) },
        text = stringResource(id = stringRes),
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary)
}

@Suppress("UNCHECKED_CAST")
@Composable
private fun DrawDebugScreen(sleepViewModelData: ViewModelData,
                            heartRateViewModelData: ViewModelData,
                            stepsViewModelData: ViewModelData,
                            bmrViewModelData: ViewModelData,
                            bloodGlucoseViewModelData: ViewModelData,
                            windowSize: WindowSize,
                            onError: (Throwable?) -> Unit = {})
{
    val expanderElements = remember {ExpanderElements(5) }

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
    val bloodGlucoseViewMode: BloodGlucoseViewModel =
        viewModel(factory = BloodGlucoseViewModel.Factory)

    DrawDebugScreen(
        sleepViewModelData = sleepSessionViewModel.getViewModelData(),
        heartRateViewModelData = heartRateViewModel.getViewModelData(),
        stepsViewModelData = stepsViewModel.getViewModelData(),
        bmrViewModelData = basalMetabolicRateViewModel.getViewModelData(),
        bloodGlucoseViewModelData = bloodGlucoseViewMode.getViewModelData(),
        windowSize = windowSize,
        onError = onError)
}
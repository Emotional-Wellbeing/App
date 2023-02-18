package es.upm.bienestaremocional.app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.ui.component.animation.DisplayLottieAnimation
import es.upm.bienestaremocional.app.ui.component.onboarding.HorizontalPagerContent
import es.upm.bienestaremocional.app.ui.component.onboarding.OnboardingContent
import es.upm.bienestaremocional.app.ui.screen.destinations.HomeScreenDestination
import es.upm.bienestaremocional.app.ui.viewmodel.OnboardingViewModel
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Destination
@Composable
fun OnboardingScreen(navigator: DestinationsNavigator,
                     windowSize: WindowSize,
                     viewModel: OnboardingViewModel = hiltViewModel()
)
{
    val pagerState = rememberPagerState()
    OnboardingScreen(windowSize = windowSize,
        pagerState = pagerState,
        onPreviousPage = {viewModel.onPreviousPage(pagerState)},
        onNextPage =  {viewModel.onNextPage(pagerState)},
        onFinish = {
            viewModel.onFinish()
            navigator.popBackStack()
            navigator.navigate(HomeScreenDestination)
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun OnboardingScreen(windowSize: WindowSize,
                             pagerState: PagerState,
                             onNextPage: () -> Unit,
                             onPreviousPage: () -> Unit,
                             onFinish: () -> Unit)
{
    Surface()
    {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp))
        {
            val items = OnboardingContent.content
            HorizontalPager(
                count = items.size,
                state = pagerState,
                modifier = Modifier.weight(1f)
            )
            {
                    page -> DrawPage(horizontalPagerContent = items[page],
                pagerState = pagerState,
                windowSize = windowSize,
                onNextPage = onNextPage,
                onPreviousPage = onPreviousPage,
                onFinish = onFinish)
            }
        }
    }
}



@OptIn(ExperimentalPagerApi::class)
@Composable
private fun DrawPage(horizontalPagerContent: HorizontalPagerContent,
             pagerState : PagerState,
             windowSize: WindowSize,
             onNextPage: () -> Unit,
             onPreviousPage: () -> Unit,
             onFinish: () -> Unit
)
{
    //content
    Column(modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        //image
        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(2.25f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            DisplayLottieAnimation(rawRes = horizontalPagerContent.animation,
                animationLoop = horizontalPagerContent.animationLoop,
            )
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            verticalArrangement = Arrangement.SpaceAround)
        {
            //label
            Text(
                text = stringResource(id = horizontalPagerContent.title),
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = if (windowSize == WindowSize.COMPACT)
                    MaterialTheme.typography.titleMedium
                else
                    MaterialTheme.typography.titleLarge
            )

            //description
            Text(
                text = stringResource(id = horizontalPagerContent.content),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = if (windowSize == WindowSize.COMPACT)
                    MaterialTheme.typography.bodyMedium
                else
                    MaterialTheme.typography.bodyLarge
            )

            //footer
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically)
            {
                if (pagerState.currentPage == 0)
                {
                    TextButton(onClick = onFinish)
                    {
                        Text(text = stringResource(id = R.string.skip))
                    }
                }
                else
                {
                    TextButton(onClick = onPreviousPage)
                    {
                        Text(text = stringResource(id = R.string.go_back))
                    }
                }

                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = MaterialTheme.colorScheme.primary)

                if (pagerState.currentPage == pagerState.pageCount - 1) {
                    TextButton(onClick = onFinish)
                    {
                        Text(text = stringResource(id = R.string.finish))
                    }
                }
                else
                {
                    TextButton(onClick = onNextPage)
                    {
                        Text(text = stringResource(id = R.string.next))
                    }
                }
            }
        }
    }
}

/*
 * Previews are not shown properly in Android Studio, but if they are executed on device they are
 * shown successfully
 */

@OptIn(ExperimentalPagerApi::class)
@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun OnboardingScreenPreview()
{
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val onPreviousPage : () -> Unit = {
        coroutineScope.launch {
            pagerState.animateScrollToPage(
                pagerState.currentPage - 1,
                pagerState.currentPageOffset
            )
        }
    }
    val onNextPage : () -> Unit = {
        coroutineScope.launch {
            pagerState.animateScrollToPage(
                pagerState.currentPage + 1,
                pagerState.currentPageOffset
            )
        }
    }
    BienestarEmocionalTheme {
        OnboardingScreen(windowSize = WindowSize.COMPACT,
            pagerState = pagerState,
            onPreviousPage = onPreviousPage,
            onNextPage = onNextPage,
            onFinish = {})
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun OnboardingScreenPreviewDarkTheme()
{
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val onPreviousPage : () -> Unit = {
        coroutineScope.launch {
            pagerState.animateScrollToPage(
                pagerState.currentPage - 1,
                pagerState.currentPageOffset
            )
        }
    }
    val onNextPage : () -> Unit = {
        coroutineScope.launch {
            pagerState.animateScrollToPage(
                pagerState.currentPage + 1,
                pagerState.currentPageOffset
            )
        }
    }

    BienestarEmocionalTheme(darkTheme = true) {
        OnboardingScreen(windowSize = WindowSize.COMPACT,
            pagerState = pagerState,
            onPreviousPage = onPreviousPage,
            onNextPage = onNextPage,
            onFinish = {})
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun OnboardingScreenNotCompactPreview()
{
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val onPreviousPage : () -> Unit = {
        coroutineScope.launch {
            pagerState.animateScrollToPage(
                pagerState.currentPage - 1,
                pagerState.currentPageOffset
            )
        }
    }
    val onNextPage : () -> Unit = {
        coroutineScope.launch {
            pagerState.animateScrollToPage(
                pagerState.currentPage + 1,
                pagerState.currentPageOffset
            )
        }
    }

    BienestarEmocionalTheme {
        OnboardingScreen(windowSize = WindowSize.MEDIUM,
            pagerState = pagerState,
            onPreviousPage = onPreviousPage,
            onNextPage = onNextPage,
            onFinish = {})
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun OnboardingScreenNotCompactPreviewDarkTheme()
{
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val onPreviousPage : () -> Unit = {
        coroutineScope.launch {
            pagerState.animateScrollToPage(
                pagerState.currentPage - 1,
                pagerState.currentPageOffset
            )
        }
    }
    val onNextPage : () -> Unit = {
        coroutineScope.launch {
            pagerState.animateScrollToPage(
                pagerState.currentPage + 1,
                pagerState.currentPageOffset
            )
        }
    }
    BienestarEmocionalTheme(darkTheme = true) {
        OnboardingScreen(windowSize = WindowSize.MEDIUM,
            pagerState = pagerState,
            onPreviousPage = onPreviousPage,
            onNextPage = onNextPage,
            onFinish = {})
    }
}
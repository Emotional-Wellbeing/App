package es.upm.bienestaremocional.app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.layout.WindowMetricsCalculator
import com.google.accompanist.pager.*
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.ui.component.animation.DisplayLottieAnimation
import es.upm.bienestaremocional.app.ui.component.onboarding.HorizontalPagerContent
import es.upm.bienestaremocional.app.ui.component.onboarding.OnboardingContent
import es.upm.bienestaremocional.core.ui.responsive.WindowSizeClass
import es.upm.bienestaremocional.core.ui.responsive.computeWindowSizeClasses
import es.upm.bienestaremocional.core.ui.responsive.getActivity
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(onFinish: () -> Unit)
{
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(PaddingValues(vertical = 32.dp)))
    {
        val items = OnboardingContent.content
        val pagerState = rememberPagerState()

        HorizontalPager(
            count = items.size,
            state = pagerState,
            modifier = Modifier.weight(1f))
        { currentPage -> DrawPage(horizontalPagerContent = items[currentPage],
            pagerState = pagerState,
            onFinish = onFinish)
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun DrawPage(horizontalPagerContent: HorizontalPagerContent,
             pagerState : PagerState,
             onFinish: () -> Unit
)
{
    val windowSizeClass = LocalContext.current.getActivity()?.let {
        computeWindowSizeClasses(
            windowMetricsCalculator = WindowMetricsCalculator.getOrCreate(),
            activity = it,
            displayMetrics = LocalContext.current.resources.displayMetrics)
    }
    ?: run {
        WindowSizeClass.COMPACT
    }

    val corrutineScope = rememberCoroutineScope()

    //content
    Column(
        modifier = Modifier
            .fillMaxHeight(0.95f)
            .padding(PaddingValues(horizontal = 32.dp, vertical = 32.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top)
    {
        //image
        Column(
            Modifier
                .fillMaxHeight(0.75f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            DisplayLottieAnimation(rawRes = horizontalPagerContent.animation,
                animationLoop = horizontalPagerContent.animationLoop,
                modifier = Modifier.fillMaxSize())
        }

        Spacer(modifier = Modifier.height(16.dp))

        //text
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                text = stringResource(id = horizontalPagerContent.title),
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = horizontalPagerContent.content),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Justify,
                style = if (windowSizeClass == WindowSizeClass.COMPACT)
                    MaterialTheme.typography.bodyMedium
                else
                    MaterialTheme.typography.bodyLarge
            )
        }
    }
    //footer
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom)
    {
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
                TextButton(onClick = {
                    corrutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1,
                            pagerState.currentPageOffset)
                    }
                })
                {
                    Text(text = stringResource(id = R.string.go_back))
                }
            }

            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = MaterialTheme.colorScheme.primary,
            )

            if (pagerState.currentPage == pagerState.pageCount - 1)
            {
                TextButton(onClick = onFinish)
                {
                    Text(text = stringResource(id = R.string.finish))
                }
            }
            else
            {
                TextButton(onClick = {
                    corrutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1,
                            pagerState.currentPageOffset)
                    }
                })
                {
                    Text(text = stringResource(id = R.string.next))
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview()
{
    BienestarEmocionalTheme {
        OnboardingScreen(onFinish = {})
    }
}
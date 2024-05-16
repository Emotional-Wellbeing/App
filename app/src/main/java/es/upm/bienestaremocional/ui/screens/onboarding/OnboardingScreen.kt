@file:Suppress("KotlinDeprecation")

package es.upm.bienestaremocional.ui.screens.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import es.upm.bienestaremocional.ui.component.animation.DisplayLottieAnimation
import es.upm.bienestaremocional.ui.component.onboarding.HorizontalPagerContent
import es.upm.bienestaremocional.ui.component.onboarding.OnboardingContent
import es.upm.bienestaremocional.ui.responsive.computeWindowHeightSize
import es.upm.bienestaremocional.ui.responsive.computeWindowWidthSize
import es.upm.bienestaremocional.ui.screens.destinations.PermissionScreenDestination
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import kotlinx.coroutines.launch

/**
 * Onboarding screen using a carrousel to display information
 */
@Destination
@Composable
fun OnboardingScreen(
    navigator: DestinationsNavigator,
    standalone: Boolean,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    OnboardingScreen(
        heightSize = computeWindowHeightSize(),
        widthSize = computeWindowWidthSize()
    )
    {
        viewModel.onFinish()
        navigator.popBackStack()

        if (!standalone)
            navigator.navigate(PermissionScreenDestination)
    }
}

@Composable
private fun OnboardingScreen(
    heightSize: WindowHeightSizeClass,
    widthSize: WindowWidthSizeClass,
    onFinish: () -> Unit
) {
    val items = remember { OnboardingContent.content }
    val pagerState = rememberPagerState()

    Surface()
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
        {
            HorizontalPager(
                count = items.size,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->

                DrawPage(
                    horizontalPagerContent = items[page],
                    pagerState = pagerState,
                    heightSize = heightSize,
                    widthSize = widthSize,
                    onFinish = onFinish
                )
            }
        }
    }
}


/**
 * Draws a single page of onboarding
 */
@Composable
private fun DrawPage(
    horizontalPagerContent: HorizontalPagerContent,
    pagerState: PagerState,
    heightSize: WindowHeightSizeClass,
    widthSize: WindowWidthSizeClass,
    onFinish: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val onPreviousPage: () -> Unit = {
        coroutineScope.launch {
            pagerState.animateScrollToPage(
                pagerState.currentPage - 1,
                pagerState.currentPageOffset
            )
        }
    }
    val onNextPage: () -> Unit = {
        coroutineScope.launch {
            pagerState.animateScrollToPage(
                pagerState.currentPage + 1,
                pagerState.currentPageOffset
            )
        }
    }

    //content
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        //Image
        DisplayLottieAnimation(
            rawRes = horizontalPagerContent.animation,
            modifier = Modifier.weight(1f),
            animationLoop = horizontalPagerContent.animationLoop,
        )

        //label
        Text(
            text = stringResource(id = horizontalPagerContent.title),
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),

            style = if (widthSize >= WindowWidthSizeClass.Medium
                && heightSize >= WindowHeightSizeClass.Medium
            )
                MaterialTheme.typography.titleLarge
            else
                MaterialTheme.typography.titleMedium
        )

        //description
        Text(
            text = stringResource(id = horizontalPagerContent.content),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            style = if (widthSize >= WindowWidthSizeClass.Medium
                && heightSize >= WindowHeightSizeClass.Medium
            )
                MaterialTheme.typography.bodyLarge
            else
                MaterialTheme.typography.bodyMedium
        )

        //footer
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            if (pagerState.currentPage == 0) {
                TextButton(onClick = onFinish)
                {
                    Text(text = stringResource(id = R.string.skip))
                }
            }
            else {
                TextButton(onClick = onPreviousPage)
                {
                    Text(text = stringResource(id = R.string.go_back))
                }
            }

            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = MaterialTheme.colorScheme.primary
            )

            if (pagerState.currentPage == pagerState.pageCount - 1) {
                TextButton(onClick = onFinish)
                {
                    Text(text = stringResource(id = R.string.finish))
                }
            }
            else {
                TextButton(onClick = onNextPage)
                {
                    Text(text = stringResource(id = R.string.next))
                }
            }
        }
    }
}

/*
 * Previews are not shown properly in Android Studio, but if they are executed on device they are
 * shown successfully
 */

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun OnboardingScreenPreview() {
    BienestarEmocionalTheme {
        OnboardingScreen(heightSize = WindowHeightSizeClass.Compact,
            widthSize = WindowWidthSizeClass.Compact,
            onFinish = {})
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun OnboardingScreenPreviewDarkTheme() {
    BienestarEmocionalTheme(darkTheme = true) {
        OnboardingScreen(heightSize = WindowHeightSizeClass.Compact,
            widthSize = WindowWidthSizeClass.Compact,
            onFinish = {})
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun OnboardingScreenNotCompactPreview() {
    BienestarEmocionalTheme {
        OnboardingScreen(heightSize = WindowHeightSizeClass.Medium,
            widthSize = WindowWidthSizeClass.Medium,
            onFinish = {})
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun OnboardingScreenNotCompactPreviewDarkTheme() {
    BienestarEmocionalTheme(darkTheme = true) {
        OnboardingScreen(heightSize = WindowHeightSizeClass.Medium,
            widthSize = WindowWidthSizeClass.Medium,
            onFinish = {})
    }
}
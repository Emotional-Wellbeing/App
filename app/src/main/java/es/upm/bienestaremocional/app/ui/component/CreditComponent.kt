package es.upm.bienestaremocional.app.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.window.layout.WindowMetricsCalculator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.credits.Credit
import es.upm.bienestaremocional.core.ui.responsive.WindowSizeClass
import es.upm.bienestaremocional.core.ui.responsive.computeWindowSizeClasses
import es.upm.bienestaremocional.core.ui.responsive.getActivity
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

@Composable
private fun nameStyle(windowSizeClass: WindowSizeClass, importantContribution: Boolean) =
    if (windowSizeClass == WindowSizeClass.COMPACT)
    {
        if (importantContribution)
            MaterialTheme.typography.titleMedium
        else
            MaterialTheme.typography.bodyMedium
    }
    else
    {
        if (importantContribution)
            MaterialTheme.typography.titleLarge
        else
            MaterialTheme.typography.bodyLarge
    }

@Composable
private fun descriptionStyle(windowSizeClass: WindowSizeClass, importantContribution: Boolean) =
    if (windowSizeClass == WindowSizeClass.COMPACT)
    {
        if (importantContribution)
            MaterialTheme.typography.bodyMedium
        else
            MaterialTheme.typography.labelMedium
    }
    else
    {
        if (importantContribution)
            MaterialTheme.typography.bodyLarge
        else
            MaterialTheme.typography.labelLarge
    }

@Composable
fun CreditComponent(credit: Credit)
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

    Column()
    {
        Text(text = stringResource(id = credit.nameResource),
            textAlign = TextAlign.Justify,
            color = MaterialTheme.colorScheme.primary,
            style = nameStyle(windowSizeClass,credit.importantContribution)
        )
        Text(text = stringResource(id = credit.descriptionResource),
            textAlign = TextAlign.Justify,
            color = MaterialTheme.colorScheme.onBackground,
            style = descriptionStyle(windowSizeClass,credit.importantContribution)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun CreditImportantComponentPreview()
{
    BienestarEmocionalTheme {
        CreditComponent(
            Credit(
                nameResource = R.string.credit_author_name,
                descriptionResource = R.string.credit_author_description,
                importantContribution = true)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CreditImportantComponentPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        CreditComponent(
            Credit(
                nameResource = R.string.credit_author_name,
                descriptionResource = R.string.credit_author_description,
                importantContribution = true)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CreditNotImportantComponentPreview()
{
    BienestarEmocionalTheme {
        CreditComponent(
            Credit(
                nameResource = R.string.credit_logo1_name,
                descriptionResource = R.string.credit_logo1_description,
                importantContribution = false)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CreditNotImportantComponentPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        CreditComponent(
            Credit(
                nameResource = R.string.credit_logo1_name,
                descriptionResource = R.string.credit_logo1_description,
                importantContribution = false
            )
        )
    }
}
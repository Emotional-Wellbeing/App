package es.upm.bienestaremocional.app.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.credits.Credit
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Provides the style used for  [Credit.nameResource] depending of [WindowSize]
 * and [Credit.importantContribution]
 * @param windowSize: instance of [WindowSize] indicating the type of screen
 * @param importantContribution: [Credit.importantContribution] value
 * @see Credit
 * @see WindowSize
 */
@Composable
private fun nameStyle(windowSize: WindowSize, importantContribution: Boolean) =
    if (windowSize == WindowSize.COMPACT)
    {
        if (importantContribution)
            MaterialTheme.typography.titleMedium
        else
            MaterialTheme.typography.bodyMedium
    }
    else
    {
        if (importantContribution)
            MaterialTheme.typography.headlineSmall
        else
            MaterialTheme.typography.titleSmall
    }

/**
 * Provides the style used for [Credit.descriptionResource] depending of [WindowSize]
 * and [Credit.importantContribution]
 * @param windowSize: instance of [WindowSize] indicating the type of screen
 * @param importantContribution: [Credit.importantContribution] value
 * @see Credit
 * @see WindowSize
 */
@Composable
private fun descriptionStyle(windowSize: WindowSize, importantContribution: Boolean) =
    if (windowSize == WindowSize.COMPACT)
    {
        if (importantContribution)
            MaterialTheme.typography.bodyMedium
        else
            MaterialTheme.typography.labelMedium
    }
    else
    {
        if (importantContribution)
            MaterialTheme.typography.titleMedium
        else
            MaterialTheme.typography.bodyMedium
    }

/**
 * Display Credit information inside a column
 * @param credit: element to display
 * @param windowSize: Type of [WindowSize] used for calibrate Texts
 * @see Credit
 * @see WindowSize
 */
@Composable
fun CreditComponent(credit: Credit, windowSize: WindowSize)
{
    Column()
    {
        Text(text = stringResource(id = credit.nameResource),
            textAlign = TextAlign.Justify,
            color = MaterialTheme.colorScheme.secondary,
            style = nameStyle(windowSize,credit.importantContribution)
        )
        Text(text = stringResource(id = credit.descriptionResource),
            textAlign = TextAlign.Justify,
            color = MaterialTheme.colorScheme.onBackground,
            style = descriptionStyle(windowSize,credit.importantContribution)
        )
    }

}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun CreditImportantComponentPreview()
{
    BienestarEmocionalTheme {
        Surface {
            CreditComponent(
                credit = Credit(
                    nameResource = R.string.credit_author_name,
                    descriptionResource = R.string.credit_author_description,
                    importantContribution = true
                ),
                windowSize = WindowSize.COMPACT
            )
        }
    }
}


@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun CreditImportantComponentPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            CreditComponent(
                credit = Credit(
                    nameResource = R.string.credit_author_name,
                    descriptionResource = R.string.credit_author_description,
                    importantContribution = true
                ),
                windowSize = WindowSize.COMPACT
            )
        }
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun CreditImportantComponentPreviewMediumScreen()
{
    BienestarEmocionalTheme {
        Surface {
            CreditComponent(
                credit = Credit(
                    nameResource = R.string.credit_author_name,
                    descriptionResource = R.string.credit_author_description,
                    importantContribution = true
                ),
                windowSize = WindowSize.MEDIUM
            )
        }
    }
}


@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun CreditImportantComponentPreviewDarkThemeMediumScreen()
{
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            CreditComponent(
                credit = Credit(
                    nameResource = R.string.credit_author_name,
                    descriptionResource = R.string.credit_author_description,
                    importantContribution = true
                ),
                windowSize = WindowSize.MEDIUM
            )
        }
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun CreditNotImportantComponentPreview()
{
    BienestarEmocionalTheme {
        Surface {
            CreditComponent(
                credit = Credit(
                    nameResource = R.string.credit_logo1_name,
                    descriptionResource = R.string.credit_logo1_description,
                    importantContribution = false
                ),
                windowSize = WindowSize.COMPACT
            )
        }
    }
}


@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun CreditNotImportantComponentPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            CreditComponent(
                credit = Credit(
                    nameResource = R.string.credit_logo1_name,
                    descriptionResource = R.string.credit_logo1_description,
                    importantContribution = false
                ),
                windowSize = WindowSize.COMPACT
            )
        }
    }
}


@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun CreditNotImportantComponentPreviewMediumScreen()
{
    BienestarEmocionalTheme {
        Surface {
            CreditComponent(
                credit = Credit(
                    nameResource = R.string.credit_logo1_name,
                    descriptionResource = R.string.credit_logo1_description,
                    importantContribution = false
                ),
                windowSize = WindowSize.MEDIUM
            )
        }
    }
}


@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun CreditNotImportantComponentPreviewDarkThemeMediumScreen()
{
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            CreditComponent(
                credit = Credit(
                    nameResource = R.string.credit_logo1_name,
                    descriptionResource = R.string.credit_logo1_description,
                    importantContribution = false
                ),
                windowSize = WindowSize.MEDIUM
            )
        }
    }
}
package es.upm.bienestaremocional.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.credits.Credit
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

/**
 * Provides the style used for [Credit.nameResource] depending of the dimensions
 * of the screen and [Credit.importantContribution]
 * @param widthSize [WindowWidthSizeClass] of the screen
 * @param heightSize [WindowHeightSizeClass] of the screen
 * @param importantContribution: [Credit.importantContribution] value
 * @see Credit
 */
@Composable
private fun nameStyle(widthSize: WindowWidthSizeClass,
                      heightSize: WindowHeightSizeClass,
                      importantContribution: Boolean) =
    if (widthSize >= WindowWidthSizeClass.Medium && heightSize >= WindowHeightSizeClass.Medium)
    {
        if (importantContribution)
            MaterialTheme.typography.headlineSmall
        else
            MaterialTheme.typography.titleSmall
    }
    else
    {
        if (importantContribution)
            MaterialTheme.typography.titleMedium
        else
            MaterialTheme.typography.bodyMedium
    }

/**
 * Provides the style used for [Credit.descriptionResource] depending of the dimensions
 * of the screen and [Credit.importantContribution]
 * @param widthSize [WindowWidthSizeClass] of the screen
 * @param heightSize [WindowHeightSizeClass] of the screen
 * @param importantContribution: [Credit.importantContribution] value
 * @see Credit
 */
@Composable
private fun descriptionStyle(widthSize: WindowWidthSizeClass,
                             heightSize: WindowHeightSizeClass,
                             importantContribution: Boolean) =
    if (widthSize >= WindowWidthSizeClass.Medium && heightSize >= WindowHeightSizeClass.Medium)
    {
        if (importantContribution)
            MaterialTheme.typography.titleMedium
        else
            MaterialTheme.typography.bodyMedium
    }
    else
    {
        if (importantContribution)
            MaterialTheme.typography.bodyMedium
        else
            MaterialTheme.typography.labelMedium
    }

/**
 * Display Credit information inside a column
 * @param credit element to display
 * @param widthSize [WindowWidthSizeClass] of the screen
 * @param heightSize [WindowHeightSizeClass] of the screen
 * @see Credit
 */
@Composable
fun CreditComponent(credit: Credit,
                    widthSize: WindowWidthSizeClass,
                    heightSize: WindowHeightSizeClass)
{
    Column()
    {
        Text(text = stringResource(id = credit.nameResource),
            textAlign = TextAlign.Justify,
            color = MaterialTheme.colorScheme.secondary,
            style = nameStyle(widthSize, heightSize, credit.importantContribution)
        )
        Text(text = stringResource(id = credit.descriptionResource),
            textAlign = TextAlign.Justify,
            color = MaterialTheme.colorScheme.onBackground,
            style = descriptionStyle(widthSize, heightSize, credit.importantContribution)
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
                    nameResource = R.string.credit_author1_name,
                    descriptionResource = R.string.credit_author1_description,
                    importantContribution = true
                ),
                widthSize = WindowWidthSizeClass.Compact,
                heightSize = WindowHeightSizeClass.Compact
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
                    nameResource = R.string.credit_author1_name,
                    descriptionResource = R.string.credit_author1_description,
                    importantContribution = true
                ),
                widthSize = WindowWidthSizeClass.Compact,
                heightSize = WindowHeightSizeClass.Compact
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
                    nameResource = R.string.credit_author1_name,
                    descriptionResource = R.string.credit_author1_description,
                    importantContribution = true
                ),
                widthSize = WindowWidthSizeClass.Medium,
                heightSize = WindowHeightSizeClass.Medium
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
                    nameResource = R.string.credit_author1_name,
                    descriptionResource = R.string.credit_author1_description,
                    importantContribution = true
                ),
                widthSize = WindowWidthSizeClass.Medium,
                heightSize = WindowHeightSizeClass.Medium
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
                widthSize = WindowWidthSizeClass.Compact,
                heightSize = WindowHeightSizeClass.Compact
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
                widthSize = WindowWidthSizeClass.Compact,
                heightSize = WindowHeightSizeClass.Compact
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
                widthSize = WindowWidthSizeClass.Medium,
                heightSize = WindowHeightSizeClass.Medium
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
                widthSize = WindowWidthSizeClass.Medium,
                heightSize = WindowHeightSizeClass.Medium
            )
        }
    }
}

package es.upm.sistemabienestaremocional.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.upm.sistemabienestaremocional.R
import es.upm.sistemabienestaremocional.theme.SistemaBienestarEmocionalTheme

/**
 * Shows the privacy policy.
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PrivacyPolicyScreen(onBackClick : () -> Unit)
{
    Scaffold(
        topBar =
        {
            TopAppBar(
                title = { Text(stringResource(id = R.string.privacy_policy))},
                navigationIcon =
                {
                    IconButton(onClick = onBackClick)
                    {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Volver atrás"
                        )
                    }
                }
            )
        },
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.privacy_policy_description))
        }
    }

}

@Preview
@Composable
fun PrivacyPolicyScreenPreview()
{
    SistemaBienestarEmocionalTheme() {
        PrivacyPolicyScreen(onBackClick = {})
    }
}

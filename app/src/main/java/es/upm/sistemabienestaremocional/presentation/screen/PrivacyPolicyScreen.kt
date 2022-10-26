
package es.upm.sistemabienestaremocional.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import es.upm.sistemabienestaremocional.R


/**
 * Shows the privacy policy.
 */
@Composable
fun PrivacyPolicyScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.privacy_policy))
        Spacer(modifier = Modifier.height(32.dp))
        Text(stringResource(R.string.privacy_policy_description))
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                /* going back to the main screen */
                navController.navigateUp()
            }
        ) {
            Text(text = "Go back")
        }
    }
}

/*@Preview
@Composable
fun PrivacyPolicyScreenPreview() {
    SistemaBienestarEmocionalTheme() {
        PrivacyPolicyScreen()
    }
}*/

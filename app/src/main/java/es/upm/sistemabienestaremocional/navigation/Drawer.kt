package es.upm.sistemabienestaremocional.navigation

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.upm.sistemabienestaremocional.R

const val HEALTH_CONNECT_SETTINGS_ACTION = "androidx.health.ACTION_HEALTH_CONNECT_SETTINGS"

@Composable
fun Drawer()
{
    val activity = LocalContext.current
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp),
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = {
                        val settingsIntent = Intent()
                        settingsIntent.action = HEALTH_CONNECT_SETTINGS_ACTION
                        activity.startActivity(settingsIntent)
                    }
                )
        )
        {
            Icon(
                modifier = Modifier
                    .padding(all =  2.dp)
                    .size(size = 28.dp),
                painter = painterResource(id = R.drawable.settings),
                contentDescription = "Settings",
                tint = Black
            )

            // label
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(R.string.health_connect_settings),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
            )
        }
    }

}

package es.upm.sistemabienestaremocional.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import es.upm.sistemabienestaremocional.theme.SistemaBienestarEmocionalTheme

@Composable
private fun DrawItem(entry: MenuEntry,
                     repintIcon: Boolean = true,
                     itemClick: () -> Unit)
{
    /**
     * @Todo create text size options responsive
     * @Todo create color options according to light/dark theme
     */

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                itemClick()
            }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
        )
    {

        Icon(
            modifier = Modifier
                .padding(all = 2.dp)
                .size(size = 28.dp),
            painter = painterResource(entry.iconId),
            contentDescription = stringResource(id = entry.labelId),
            tint = if(repintIcon) Color.Black else Color.Unspecified
        )

        // label
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(id = entry.labelId),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}

@Composable
fun GetDrawerItems(navController: NavController)
{
    val context = LocalContext.current

    LocalMenuEntry.values().forEach {
        element -> DrawItem(entry = element) {
            navController.navigate(element.route)
        }
    }

    ForeignMenuEntry.values().forEach {
        element -> DrawItem(entry = element, repintIcon = false) {
            openForeignActivity(context = context, action = element.action)
        }
    }
}

@Preview
@Composable
fun DrawLocalItemPreview()
{
    SistemaBienestarEmocionalTheme {
        DrawItem(LocalMenuEntry.HomeScreen){}
    }
}

@Preview
@Composable
fun DrawForeignItemPreview()
{
    SistemaBienestarEmocionalTheme {
        DrawItem(ForeignMenuEntry.HealthConnectScreen,false){}
    }
}
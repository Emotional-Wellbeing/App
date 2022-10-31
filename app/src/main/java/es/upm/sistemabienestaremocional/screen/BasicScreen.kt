package es.upm.sistemabienestaremocional.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import es.upm.sistemabienestaremocional.R
import es.upm.sistemabienestaremocional.theme.SistemaBienestarEmocionalTheme

/**
 * Define the base detail screen used in the app
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(idTitle : Int, onBackClick : () -> Unit, content: @Composable (PaddingValues) -> Unit)
{
    Scaffold(
        topBar =
        {
            TopAppBar(
                title = { Text(stringResource(id = idTitle)) },
                navigationIcon =
                {
                    IconButton(onClick = onBackClick)
                    {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = stringResource(R.string.go_back)
                        )
                    }
                }
            )
        },
        modifier = Modifier
            .fillMaxSize(),
            //padding is not realised here because it affect top bar
        content = content
    )
}

@Preview
@Composable
fun DetailScreenPreview()
{
    SistemaBienestarEmocionalTheme()
    {
        DetailScreen(idTitle = R.string.demo_screen_label,
            onBackClick = {},
            content = {})
    }
}
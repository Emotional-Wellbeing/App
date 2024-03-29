package es.upm.bienestaremocional.ui.component

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import es.upm.bienestaremocional.utils.getActivity

@Composable
fun BackHandlerMinimizeApp(activity: Activity) {
    BackHandler(enabled = true, onBack = {
        activity.moveTaskToBack(true)
    })
}

@Composable
fun BackHandlerMinimizeApp(context: Context) {
    BackHandlerMinimizeApp(context.getActivity())
}

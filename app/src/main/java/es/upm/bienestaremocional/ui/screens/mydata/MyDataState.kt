package es.upm.bienestaremocional.ui.screens.mydata

sealed class MyDataState {
    object NoSelection : MyDataState()
    data class Selected(val index: Int) : MyDataState()
}
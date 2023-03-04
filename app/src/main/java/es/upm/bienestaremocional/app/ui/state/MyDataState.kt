package es.upm.bienestaremocional.app.ui.state

sealed class MyDataState {
    object NoSelection : MyDataState()
    data class Selected(val index: Int) : MyDataState()
}
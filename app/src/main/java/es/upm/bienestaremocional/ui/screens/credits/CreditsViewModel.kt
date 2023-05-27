package es.upm.bienestaremocional.ui.screens.credits

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.credits.Credit
import javax.inject.Inject

@HiltViewModel
class CreditsViewModel @Inject constructor(private val credits: List<Credit>): ViewModel()
{
    val importantPeople get() = credits.filter { credit -> credit.importantContribution }
    val notImportantPeople get() = credits.filter { credit -> !credit.importantContribution }
}
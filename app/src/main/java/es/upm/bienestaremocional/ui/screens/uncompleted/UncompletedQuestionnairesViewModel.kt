package es.upm.bienestaremocional.ui.screens.uncompleted

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.database.entity.round.DailyRoundFull
import es.upm.bienestaremocional.data.database.entity.round.OneOffRoundFull
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyRoundFullRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffRoundFullRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UncompletedQuestionnairesViewModel @Inject constructor(
    private val dailyRoundFullRepository: DailyRoundFullRepository,
    private val oneOffRoundFullRepository: OneOffRoundFullRepository,
) : ViewModel() {

    private val _dailyRoundsUncompleted = MutableLiveData<List<DailyRoundFull>>()
    val dailyRoundsUncompleted: LiveData<List<DailyRoundFull>>
        get() = _dailyRoundsUncompleted
    private val _oneOffRoundsUncompleted = MutableLiveData<List<OneOffRoundFull>>()
    val oneOffRoundsUncompleted: LiveData<List<OneOffRoundFull>>
        get() = _oneOffRoundsUncompleted


    init {
        viewModelScope.launch {
            _dailyRoundsUncompleted.value = dailyRoundFullRepository.getAllUncompleted()
            _oneOffRoundsUncompleted.value = oneOffRoundFullRepository.getAllUncompleted()
        }
    }

}

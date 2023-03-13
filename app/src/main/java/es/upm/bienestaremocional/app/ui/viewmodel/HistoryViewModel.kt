package es.upm.bienestaremocional.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.database.entity.PHQ
import es.upm.bienestaremocional.app.data.database.entity.PSS
import es.upm.bienestaremocional.app.data.database.entity.UCLA
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PHQRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PSSRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.UCLARepository
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val pssRepository: PSSRepository,
    private val phqRepository: PHQRepository,
    private val uclaRepository: UCLARepository
) : ViewModel()
{
    private val _pssData = MutableLiveData<List<PSS>>()
    val pssData: LiveData<List<PSS>>
        get() = _pssData

    private val _phqData = MutableLiveData<List<PHQ>>()
    val phqData: LiveData<List<PHQ>>
        get() = _phqData

    private val _uclaData = MutableLiveData<List<UCLA>>()
    val uclaData: LiveData<List<UCLA>>
        get() = _uclaData

    fun fetchPSSData()
    {
        viewModelScope.launch {
            _pssData.value = pssRepository.getAll()
        }
    }

    fun fetchPHQData()
    {
        viewModelScope.launch {
            _phqData.value = phqRepository.getAllFromLastSevenDays()
        }
    }

    fun fetchUCLAData()
    {
        viewModelScope.launch {
            _uclaData.value = uclaRepository.getAllFromLastSevenDays()
        }
    }
}
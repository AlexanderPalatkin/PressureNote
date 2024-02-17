package com.example.pressurenote.ui.healthlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pressurenote.model.Health
import com.example.pressurenote.model.Indication
import com.example.pressurenote.repository.HealthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HealthListViewModel @Inject constructor(private val repository: HealthRepository) :
    ViewModel() {

    private val _healthLiveData = MutableLiveData<List<Health>>()
    val healthLiveData: LiveData<List<Health>> = _healthLiveData

    init {
        getHealth()
    }

    private fun getHealth() {
        viewModelScope.launch {
            val health = repository.getHealth()
            _healthLiveData.value = health
        }
    }

    fun setIndication(indication: Indication) {

        viewModelScope.launch {
            repository.setIndications(indication)

            val updatedHealth = repository.getHealth()
            _healthLiveData.value = updatedHealth
        }
    }
}
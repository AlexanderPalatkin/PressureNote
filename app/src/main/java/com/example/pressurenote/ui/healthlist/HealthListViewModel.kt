package com.example.pressurenote.ui.healthlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pressurenote.model.AppState
import com.example.pressurenote.model.Health
import com.example.pressurenote.model.Indication
import com.example.pressurenote.repository.HealthRepository
import com.google.firebase.database.DatabaseError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HealthListViewModel @Inject constructor(private val repository: HealthRepository) :
    ViewModel() {

    private val _healthLiveData = MutableLiveData<AppState<List<Health>, DatabaseError>>()
    val healthLiveData: LiveData<AppState<List<Health>, DatabaseError>> = _healthLiveData

    init {
        getHealth()
    }

    private fun getHealth() {
        viewModelScope.launch {
            _healthLiveData.value = AppState.Loading
            val health = repository.getHealth()
            _healthLiveData.value = health
        }
    }

    fun setIndication(indication: Indication) {

        viewModelScope.launch {
            repository.setIndication(indication)

            val updatedHealth = repository.getHealth()
            _healthLiveData.value = updatedHealth
        }
    }

    fun deleteIndication(indication: Indication) {
        viewModelScope.launch {
            repository.deleteIndication(indication)

            val updatedHealth = repository.getHealth()
            _healthLiveData.value = updatedHealth
        }
    }
}
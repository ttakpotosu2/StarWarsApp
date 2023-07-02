package com.example.starwarsapp.presentation.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.domain.repository.FilmRepository
import com.example.starwarsapp.domain.repository.VehicleRepository
import com.example.starwarsapp.presentation.FilmStates
import com.example.starwarsapp.presentation.VehiclesStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleViewModel @Inject constructor(
    private val repository: VehicleRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _vehicle = mutableStateOf<VehiclesStates>(VehiclesStates.Loading)
    val vehicle: State<VehiclesStates> = _vehicle

    init {
        savedStateHandle.get<String>("vehicleId")?.let { vehicleById(it) }
    }

    private fun vehicleById(vehicleId: String){
        viewModelScope.launch{
            val result = repository.getVehicles(vehicleId)
            _vehicle.value = VehiclesStates.Success(data = result)
        }
    }
}
package com.example.starwarsapp.presentation.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.data.repositories.PlanetRepository
import com.example.starwarsapp.presentation.PlanetStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetViewModel @Inject constructor(
    private val repository: PlanetRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _planet = mutableStateOf<PlanetStates>(PlanetStates.Loading)
    val planet: State<PlanetStates> = _planet

    init {
        savedStateHandle.get<String>("planetId")?.let { planetById(it) }
    }

    private fun planetById(planetId: String){
        viewModelScope.launch{
            val result = repository.getPlanets(planetId)
            _planet.value = PlanetStates.Success(data = result)
        }
    }
}
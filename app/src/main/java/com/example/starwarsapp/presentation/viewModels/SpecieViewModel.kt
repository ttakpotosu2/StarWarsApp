package com.example.starwarsapp.presentation.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.data.repositories.SpecieRepository
import com.example.starwarsapp.presentation.SpeciesStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpecieViewModel @Inject constructor(
    private val repository: SpecieRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _specie = mutableStateOf<SpeciesStates>(SpeciesStates.Loading)
    val specie: State<SpeciesStates> = _specie

    init {
        savedStateHandle.get<String>("specieId")?.let { specieById(it) }
    }

    private fun specieById(specieId: String){
        viewModelScope.launch{
            val result = repository.getSpecies(specieId)
            _specie.value = SpeciesStates.Success(data = result)
        }
    }
}
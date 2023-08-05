package com.example.starwarsapp.presentation.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.data.repositories.StarShipRepository
import com.example.starwarsapp.presentation.StarshipsStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StarshipViewModel @Inject constructor(
    private val repository: StarShipRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _starship = mutableStateOf<StarshipsStates>(StarshipsStates.Loading)
    val sratship: State<StarshipsStates> = _starship

    init {
        savedStateHandle.get<String>("starshipId")?.let { starshipById(it) }
    }

    private fun starshipById(starshipId: String){
        viewModelScope.launch{
            val result = repository.getStarShips(starshipId)
            _starship.value = StarshipsStates.Success(data = result)
        }
    }
}
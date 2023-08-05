package com.example.starwarsapp.presentation.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.data.repositories.PersonRepository
import com.example.starwarsapp.presentation.PersonStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val repository: PersonRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _person = mutableStateOf<PersonStates>(PersonStates.Loading)
    val person: State<PersonStates> = _person

    init {
        savedStateHandle.get<String>("personId")?.let { personById(it) }
    }

    private fun personById(personId: String){
        viewModelScope.launch{
            val result = repository.getPerson(personId)
            _person.value = PersonStates.Success(data = result)
        }
    }
}
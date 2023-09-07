package com.example.starwarsapp.presentation.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.data.repositories.FilmRepository
import com.example.starwarsapp.presentation.FilmStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmViewModel @Inject constructor(
    private val repository: FilmRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _film = mutableStateOf<FilmStates>(FilmStates.Loading)
    val film: State<FilmStates> = _film

    init {
        savedStateHandle.get<String>("filmId")?.let { filmById(it) }
    }

    private fun filmById(filmId: String){
        viewModelScope.launch{
            val result = repository.getFilms(filmId)
            _film.value = FilmStates.Success(data = result)
        }
    }
}//TODO: add loader, add paging
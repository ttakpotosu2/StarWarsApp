package com.example.starwarsapp.presentation

import com.example.starwarsapp.domain.repository.FilmInfo
import com.example.starwarsapp.domain.repository.PersonInfo
import com.example.starwarsapp.domain.repository.PlanetInfo
import com.example.starwarsapp.domain.repository.SpecieInfo
import com.example.starwarsapp.domain.repository.StarshipInfo
import com.example.starwarsapp.domain.repository.VehicleInfo

sealed class FilmStates {
    object Loading: FilmStates()
    data class Success (val data: FilmInfo): FilmStates()
}

sealed class PersonStates {
    object Loading: PersonStates()
    data class Success (val data: PersonInfo): PersonStates()
}

sealed class PlanetStates {
    object Loading: PlanetStates()
    data class Success (val data: PlanetInfo): PlanetStates()
}

sealed class SpeciesStates {
    object Loading: SpeciesStates()
    data class Success (val data: SpecieInfo): SpeciesStates()
}

sealed class StarshipsStates {
    object Loading: StarshipsStates()
    data class Success (val data: StarshipInfo): StarshipsStates()
}

sealed class VehiclesStates {
    object Loading: VehiclesStates()
    data class Success (val data: VehicleInfo): VehiclesStates()
}


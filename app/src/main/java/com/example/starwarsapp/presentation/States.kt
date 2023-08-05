package com.example.starwarsapp.presentation

import com.example.starwarsapp.data.repositories.FilmInfo
import com.example.starwarsapp.data.repositories.PersonInfo
import com.example.starwarsapp.data.repositories.PlanetInfo
import com.example.starwarsapp.data.repositories.SpecieInfo
import com.example.starwarsapp.data.repositories.StarshipInfo
import com.example.starwarsapp.data.repositories.VehicleInfo

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


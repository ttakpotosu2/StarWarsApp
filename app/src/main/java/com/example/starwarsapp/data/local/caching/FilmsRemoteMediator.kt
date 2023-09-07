package com.example.starwarsapp.data.local.caching

import android.net.Uri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.starwarsapp.data.local.StarWarsDatabase
import com.example.starwarsapp.data.models.FilmsEntity
import com.example.starwarsapp.data.models.FilmsRemoteKeys
import com.example.starwarsapp.data.models.People
import com.example.starwarsapp.data.models.Planets
import com.example.starwarsapp.data.models.Species
import com.example.starwarsapp.data.models.Starships
import com.example.starwarsapp.data.models.Vehicles
import com.example.starwarsapp.data.remote.StarWarsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ExperimentalPagingApi
class FilmsRemoteMediator @Inject constructor(
    private val api: StarWarsApi,
    private val database: StarWarsDatabase
) : RemoteMediator<Int, FilmsEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FilmsEntity>
    ): MediatorResult = withContext(Dispatchers.IO) {

        return@withContext try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.next?.minus(1) ?: 1
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.next
                        ?: return@withContext MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextPage
                }
                else -> { 1 }
            }

            val filmResponse = async { api.getFilms(currentPage.toString()) }
            val peopleResponse = async { api.getPeople(currentPage.toString()) }
            val planetsResponse = async { api.getPlanets(currentPage.toString()) }
            val vehiclesResponse = async { api.getVehicles(currentPage.toString()) }
            val speciesResponse = async { api.getSpecies(currentPage.toString()) }
            val starshipsResponse = async { api.getStarships(currentPage.toString()) }

            val filmResult = filmResponse.await()
            val peopleResult = peopleResponse.await()
            val planetsResult = planetsResponse.await()
            val vehicleResult = vehiclesResponse.await()
            val speciesResult = speciesResponse.await()
            val starshipsResult = starshipsResponse.await()

            // Getting People in Film
            val personId = filmResult.results
                .flatMap { it.characters }
                .mapNotNull { Uri.parse(it).lastPathSegment }
            val peopleList = mutableListOf<People>()
            // Getting Planets in Film
            val planetId = filmResult.results
                .flatMap { it.planets }
                .mapNotNull { Uri.parse(it).lastPathSegment }
            val planetsList = mutableListOf<Planets>()
            // Getting Starships in Film
            val starshipId = filmResult.results
                .flatMap { it.starships }
                .mapNotNull { Uri.parse(it).lastPathSegment }
            val starshipsList = mutableListOf<Starships>()
            // Getting Species in Film
            val specieId = filmResult.results
                .flatMap { it.species }
                .mapNotNull { Uri.parse(it).lastPathSegment }
            val speciesList = mutableListOf<Species>()
            // Getting Vehicles in Film
            val vehicleId = filmResult.results
                .flatMap { it.vehicles }
                .mapNotNull { Uri.parse(it).lastPathSegment }
            val vehicleList = mutableListOf<Vehicles>()

            val peopleDeferred = personId.map { async { api.getPerson(it) } }
            val planetsDeferred = planetId.map { async { api.getPlanet(it) } }
            val starshipsDeferred = starshipId.map { async { api.getStarship(it) } }
            val speciesDeferred = specieId.map { async { api.getSpecie(it) } }
            val vehiclesDeferred = vehicleId.map { async { api.getVehicle(it) } }

            peopleList.addAll(peopleDeferred.awaitAll())
            planetsList.addAll(planetsDeferred.awaitAll())
            starshipsList.addAll(starshipsDeferred.awaitAll())
            speciesList.addAll(speciesDeferred.awaitAll())
            vehicleList.addAll(vehiclesDeferred.awaitAll())

            // Paging
            val endOfPaginationReached = filmResult.results.isEmpty()
            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            val remainingPeople = peopleResult.results - peopleList
            val remainingPlanets = planetsResult.results - planetsList
            val remainingVehicles = vehicleResult.results - vehicleList
            val remainingStarship = starshipsResult.results - starshipsList
            val remainingSpecies = speciesResult.results - speciesList

            database.withTransaction {
                val keys = filmResult.results.map {
                    FilmsRemoteKeys(
                        id = it.title,
                        prev = prevPage,
                        next = nextPage
                    )
                }

                database.filmsRemoteKeysDao().addFilmsRemoteKeys(remoteKeys = keys)
                database.filmsDao()
                    .addFilms(films = filmResult.results.map { it.toFilmsEntity() })

                database.peopleDao().addPeople(peopleList.map { it.toPeopleEntity() })
                database.peopleDao().updatePeople(remainingPeople.map { it.toPeopleEntity() })

                database.planetsDao().addPlanets(planetsList.map { it.toPlanetsEntity() })
                database.planetsDao()
                    .updatePlanets(remainingPlanets.map { it.toPlanetsEntity() })

                database.starshipsDao()
                    .addStarships(starshipsList.map { it.toStarshipsEntity() })
                database.starshipsDao()
                    .updateStarships(remainingStarship.map { it.toStarshipsEntity() })

                database.speciesDao().addSpecies(speciesList.map { it.toSpeciesEntity() })
                database.speciesDao()
                    .updateSpecies(remainingSpecies.map { it.toSpeciesEntity() })

                database.vehiclesDao().addVehicles(vehicleList.map { it.toVehiclesEntity() })
                database.vehiclesDao()
                    .updateVehicles(remainingVehicles.map { it.toVehiclesEntity() })
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, FilmsEntity>
    ): FilmsRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let {
                database.filmsRemoteKeysDao().getFilmsRemoteKeys(id = it.title)
            }
        }
    }

    private fun getRemoteKeyForLastItem(
        state: PagingState<Int, FilmsEntity>
    ): FilmsRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { database.filmsRemoteKeysDao().getFilmsRemoteKeys(id = it.title) }
    }
}
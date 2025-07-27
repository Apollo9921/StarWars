package com.example.starwars.networking.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.koin.StarWarsRepository
import com.example.starwars.networking.model.characters.CharactersItem
import com.example.starwars.networking.model.planets.PlanetsItem
import com.example.starwars.networking.model.ships.ShipsItem
import com.example.starwars.screens.option
import com.example.starwars.utils.network.ConnectivityObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: StarWarsRepository,
    connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _characterState =
        MutableStateFlow<CharacterState>(CharacterState.Error("Unknown error"))
    private val characterState: StateFlow<CharacterState> = _characterState

    private val _planetState = MutableStateFlow<PlanetState>(PlanetState.Error("Unknown error"))
    private val planetState: StateFlow<PlanetState> = _planetState

    private val _vehicleState = MutableStateFlow<VehicleState>(VehicleState.Error("Unknown error"))
    private val vehicleState: StateFlow<VehicleState> = _vehicleState

    var isLoading = mutableStateOf(false)
    var isSuccess = mutableStateOf(false)
    var isError = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    var allCharacters: List<CharactersItem>? = null
    var allPlanets: List<PlanetsItem>? = null
    var allVehicles: List<ShipsItem>? = null

    val networkStatus: StateFlow<ConnectivityObserver.Status> =
        connectivityObserver.observe()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = ConnectivityObserver.Status.Unavailable
            )

    init {
        viewModelScope.launch {
            when (option.value) {
                "Characters" -> getCharacters()
                "Planets" -> getPlanets()
                "Ships" -> getVehicles()
            }
        }
    }

    sealed class CharacterState {
        data class Success(val characters: List<CharactersItem>) : CharacterState()
        data class Error(val message: String) : CharacterState()
    }

    sealed class PlanetState {
        data class Success(val planets: List<PlanetsItem>) : PlanetState()
        data class Error(val message: String) : PlanetState()
    }

    sealed class VehicleState {
        data class Success(val vehicles: List<ShipsItem>) : VehicleState()
        data class Error(val message: String) : VehicleState()
    }

    fun getCharacters() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                if (networkStatus.value == ConnectivityObserver.Status.Unavailable) {
                    _characterState.value = CharacterState.Error("No Internet Connection")
                    isLoading.value = false
                    return@launch
                }
                val getCharacters = repository.getAllCharacters()
                if (getCharacters.isSuccessful && getCharacters.body() != null) {
                    _characterState.value = CharacterState.Success(getCharacters.body()!!)
                } else {
                    _characterState.value = CharacterState.Error(getCharacters.message())
                }
            } catch (e: Exception) {
                _characterState.value = CharacterState.Error(e.message ?: "Unknown error")
            } finally {
                observeCharacters()
            }
        }
    }

    private fun observeCharacters() {
        viewModelScope.launch {
            characterState.collect { state ->
                when (state) {
                    is CharacterState.Success -> {
                        allCharacters = state.characters
                        isLoading.value = false
                        isError.value = false
                        isSuccess.value = true
                    }

                    is CharacterState.Error -> {
                        errorMessage.value = state.message
                        isError.value = true
                        isLoading.value = false
                    }
                }
            }
        }
    }

    fun getPlanets() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                if (networkStatus.value == ConnectivityObserver.Status.Unavailable) {
                    _planetState.value = PlanetState.Error("No Internet Connection")
                    isLoading.value = false
                    return@launch
                }
                val getPlanets = repository.getAllPlanets()
                if (getPlanets.isSuccessful && getPlanets.body() != null) {
                    _planetState.value = PlanetState.Success(getPlanets.body()!!)
                } else {
                    _planetState.value = PlanetState.Error(getPlanets.message())
                }
            } catch (e: Exception) {
                _planetState.value = PlanetState.Error(e.message ?: "Unknown error")
            } finally {
                observePlanets()
            }
        }
    }

    private fun observePlanets() {
        viewModelScope.launch {
            planetState.collect { state ->
                when (state) {
                    is PlanetState.Error -> {
                        errorMessage.value = state.message
                        isError.value = true
                        isLoading.value = false
                    }

                    is PlanetState.Success -> {
                        allPlanets = state.planets
                        isLoading.value = false
                        isError.value = false
                        isSuccess.value = true

                    }
                }
            }
        }
    }

    fun getVehicles() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                if (networkStatus.value == ConnectivityObserver.Status.Unavailable) {
                    _vehicleState.value = VehicleState.Error("No Internet Connection")
                    isLoading.value = false
                    return@launch
                }
                val getVehicles = repository.getAllStarships()
                if (getVehicles.isSuccessful && getVehicles.body() != null) {
                    _vehicleState.value = VehicleState.Success(getVehicles.body()!!)
                } else {
                    _vehicleState.value = VehicleState.Error(getVehicles.message())
                }
            } catch (e: Exception) {
                _vehicleState.value = VehicleState.Error(e.message ?: "Unknown error")
            } finally {
                observeVehicles()
            }
        }
    }

    private fun observeVehicles() {
        viewModelScope.launch {
            vehicleState.collect { state ->
                when (state) {
                    is VehicleState.Error -> {
                        errorMessage.value = state.message
                        isError.value = true
                        isLoading.value = false
                    }

                    is VehicleState.Success -> {
                        allVehicles = state.vehicles
                        isLoading.value = false
                        isError.value = false
                        isSuccess.value = true
                    }
                }
            }
        }
    }
}
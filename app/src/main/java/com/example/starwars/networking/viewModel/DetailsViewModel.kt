package com.example.starwars.networking.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.koin.StarWarsRepository
import com.example.starwars.networking.model.characters.CharactersItem
import com.example.starwars.networking.model.planets.PlanetsItem
import com.example.starwars.networking.model.ships.ShipsItem
import com.example.starwars.utils.network.ConnectivityObserver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailsViewModel(
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

    var allCharacters: CharactersItem? = null
    var allPlanets: PlanetsItem? = null
    var allVehicles: ShipsItem? = null


    val networkStatus: StateFlow<ConnectivityObserver.Status> =
        connectivityObserver.observe()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = ConnectivityObserver.Status.Unavailable
            )


    sealed class CharacterState {
        data class Success(val characters: CharactersItem) : CharacterState()
        data class Error(val message: String) : CharacterState()
    }

    sealed class PlanetState {
        data class Success(val planets: PlanetsItem) : PlanetState()
        data class Error(val message: String) : PlanetState()
    }

    sealed class VehicleState {
        data class Success(val vehicles: ShipsItem) : VehicleState()
        data class Error(val message: String) : VehicleState()
    }

    fun getCharacter(id: Int) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                if (networkStatus.value == ConnectivityObserver.Status.Unavailable) {
                    _characterState.value = CharacterState.Error("No Internet Connection")
                    isLoading.value = false
                    return@launch
                }
                val getCharacter = repository.getCharacterById(id)
                if (getCharacter.isSuccessful && getCharacter.body() != null) {
                    _characterState.value = CharacterState.Success(getCharacter.body()!!)
                } else {
                    _characterState.value = CharacterState.Error(getCharacter.message())
                }
            } catch (e: Exception) {
                _characterState.value = CharacterState.Error(e.message ?: "Unknown error")
            } finally {
                observeCharacter()
            }
        }
    }

    private fun observeCharacter() {
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

    fun getPlanet(id: Int) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                if (networkStatus.value == ConnectivityObserver.Status.Unavailable) {
                    _planetState.value = PlanetState.Error("No Internet Connection")
                    isLoading.value = false
                    return@launch
                }
                val getPlanet = repository.getPlanetById(id)
                if (getPlanet.isSuccessful && getPlanet.body() != null) {
                    _planetState.value = PlanetState.Success(getPlanet.body()!!)
                } else {
                    _planetState.value = PlanetState.Error(getPlanet.message())
                }
            } catch (e: Exception) {
                _planetState.value = PlanetState.Error(e.message ?: "Unknown error")
            } finally {
                observePlanet()
            }
        }
    }

    private fun observePlanet() {
        viewModelScope.launch {
            planetState.collect { state ->
                when (state) {
                    is PlanetState.Success -> {
                        allPlanets = state.planets
                        isLoading.value = false
                        isError.value = false
                        isSuccess.value = true
                    }

                    is PlanetState.Error -> {
                        errorMessage.value = state.message
                        isError.value = true
                        isLoading.value = false
                    }
                }
            }
        }
    }

    fun getShip(id: Int) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                if (networkStatus.value == ConnectivityObserver.Status.Unavailable) {
                    _vehicleState.value = VehicleState.Error("No Internet Connection")
                    isLoading.value = false
                    return@launch
                }
                val getShip = repository.getStarshipById(id)
                if (getShip.isSuccessful && getShip.body() != null) {
                    _vehicleState.value = VehicleState.Success(getShip.body()!!)
                } else {
                    _vehicleState.value = VehicleState.Error(getShip.message())
                }
            } catch (e: Exception) {
                _vehicleState.value = VehicleState.Error(e.message ?: "Unknown error")
            } finally {
                observeShip()
            }
        }
    }

    private fun observeShip() {
        viewModelScope.launch {
            vehicleState.collect { state ->
                when (state) {
                    is VehicleState.Success -> {
                        allVehicles = state.vehicles
                        isLoading.value = false
                        isError.value = false
                        isSuccess.value = true
                    }

                    is VehicleState.Error -> {
                        errorMessage.value = state.message
                        isError.value = true
                        isLoading.value = false
                    }
                }
            }
        }
    }
}
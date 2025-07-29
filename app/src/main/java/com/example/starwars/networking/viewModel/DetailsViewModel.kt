package com.example.starwars.networking.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.koin.StarWarsRepository
import com.example.starwars.networking.model.characters.CharactersItem
import com.example.starwars.networking.model.planets.PlanetsItem
import com.example.starwars.networking.model.ships.ShipsItem
import com.example.starwars.utils.network.ConnectivityObserver
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for managing and providing detailed data for Star Wars entities (characters, planets, ships).
 *
 * This ViewModel is responsible for fetching individual entity details from the [StarWarsRepository]
 * based on an ID. It manages the state of these fetch operations (loading, success, error)
 * and observes network connectivity.
 *
 * @property repository The [StarWarsRepository] instance used for data retrieval.
 * @param connectivityObserver An instance of [ConnectivityObserver] to monitor network status.
 */
class DetailsViewModel(
    private val repository: StarWarsRepository,
    connectivityObserver: ConnectivityObserver
) : ViewModel() {

    // --- StateFlows for individual entity states ---

    private val _characterState =
        MutableStateFlow<CharacterState>(CharacterState.Loading) // Initial state can be Loading or Idle

    /**
     * StateFlow representing the current state of fetching character details.
     * UI can observe this to react to loading, success, or error states.
     */
    val characterState: StateFlow<CharacterState> = _characterState.asStateFlow()

    private val _planetState = MutableStateFlow<PlanetState>(PlanetState.Loading)

    /**
     * StateFlow representing the current state of fetching planet details.
     */
    val planetState: StateFlow<PlanetState> = _planetState.asStateFlow()

    private val _vehicleState = MutableStateFlow<VehicleState>(VehicleState.Loading)

    /**
     * StateFlow representing the current state of fetching ship (vehicle) details.
     */
    val vehicleState: StateFlow<VehicleState> = _vehicleState.asStateFlow()

    /** Indicates if any data loading operation is currently in progress. */
    var isLoading = mutableStateOf(false)
        private set // Make setter private to control updates from within ViewModel

    /** Indicates if the last data loading operation was successful. */
    var isSuccess = mutableStateOf(false)
        private set

    /** Indicates if an error occurred during the last data loading operation. */
    var isError = mutableStateOf(false)
        private set

    /** Stores the error message if an error occurs. */
    var errorMessage = mutableStateOf("")
        private set

    // --- Fetched Data Holders ---
    // These directly hold the fetched data. UI can observe the StateFlows above
    // and then access these properties when the state is Success.

    /** Holds the successfully fetched [CharactersItem] data. Null if not fetched or an error occurred. */
    var characterDetails: CharactersItem? = null // Renamed for clarity from allCharacters
        private set

    /** Holds the successfully fetched [PlanetsItem] data. Null if not fetched or an error occurred. */
    var planetDetails: PlanetsItem? = null // Renamed for clarity from allPlanets
        private set

    /** Holds the successfully fetched [ShipsItem] data. Null if not fetched or an error occurred. */
    var vehicleDetails: ShipsItem? = null // Renamed for clarity from allVehicles
        private set


    /**
     * StateFlow representing the current network connectivity status.
     * UI can observe this to show appropriate messages or disable/enable features.
     */
    val networkStatus: StateFlow<ConnectivityObserver.Status> =
        connectivityObserver.observe()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly, // Start observing immediately and share the last emitted value.
                initialValue = ConnectivityObserver.Status.Unavailable // Assume unavailable until first emission.
            )

    // --- Sealed classes for representing different states of data fetching ---

    /**
     * Represents the possible states for character detail fetching.
     */
    sealed class CharacterState {
        /** Indicates that character data is currently being loaded. */
        object Loading : CharacterState() // Added Loading state

        /** Indicates that character data was successfully fetched. */
        data class Success(val character: CharactersItem) : CharacterState() // Renamed for clarity

        /** Indicates that an error occurred while fetching character data. */
        data class Error(val message: String) : CharacterState()
    }

    /**
     * Represents the possible states for planet detail fetching.
     */
    sealed class PlanetState {
        object Loading : PlanetState()
        data class Success(val planet: PlanetsItem) : PlanetState() // Renamed for clarity
        data class Error(val message: String) : PlanetState()
    }

    /**
     * Represents the possible states for vehicle (ship) detail fetching.
     */
    sealed class VehicleState {
        object Loading : VehicleState()
        data class Success(val vehicle: ShipsItem) : VehicleState() // Renamed for clarity
        data class Error(val message: String) : VehicleState()
    }

    // --- Jobs for managing observer coroutines ---
    // These are useful if you need to cancel the observers explicitly, though often not necessary
    // if the collection is tied to viewModelScope and the StateFlows are managed correctly.
    private var characterObserverJob: Job? = null
    private var planetObserverJob: Job? = null
    private var shipObserverJob: Job? = null


    init {
        // Start observing the state changes as soon as the ViewModel is created.
        observeCharacter()
        observePlanet()
        observeShip()
    }

    /**
     * Fetches character details by the given [id].
     * Updates [characterState] with the result of the operation.
     * Manages [isLoading], [isSuccess], and [isError] flags based on the fetch outcome.
     * @param id The ID of the character to fetch.
     */
    fun getCharacter(id: Int) {
        viewModelScope.launch {
            _characterState.value = CharacterState.Loading // Set loading state
            isLoading.value = true
            isError.value = false
            isSuccess.value = false

            if (networkStatus.value == ConnectivityObserver.Status.Unavailable) {
                _characterState.value = CharacterState.Error("No Internet Connection")
                // isLoading is handled by observeCharacter
                return@launch
            }

            try {
                val response = repository.getCharacterById(id)
                if (response.isSuccessful && response.body() != null) {
                    _characterState.value = CharacterState.Success(response.body()!!)
                } else {
                    _characterState.value =
                        CharacterState.Error(response.message().takeIf { it.isNotEmpty() }
                            ?: "Failed to fetch character")
                }
            } catch (e: Exception) {
                _characterState.value =
                    CharacterState.Error(e.message ?: "An unknown error occurred")
            }
            // `finally` block with observeCharacter() removed as observers are now started in init.
        }
    }

    /**
     * Observes the [characterState] Flow and updates general UI state flags
     * ([isLoading], [isSuccess], [isError], [errorMessage]) and the [characterDetails] property.
     */
    private fun observeCharacter() {
        characterObserverJob?.cancel() // Cancel previous observer if any
        characterObserverJob = viewModelScope.launch {
            characterState.collect { state ->
                // Reset common flags first
                isLoading.value = state is CharacterState.Loading
                isError.value = state is CharacterState.Error
                isSuccess.value = state is CharacterState.Success

                when (state) {
                    is CharacterState.Success -> {
                        characterDetails = state.character
                        errorMessage.value = "" // Clear previous error
                    }

                    is CharacterState.Error -> {
                        errorMessage.value = state.message
                        characterDetails = null // Clear previous data
                    }

                    is CharacterState.Loading -> {
                        errorMessage.value = ""
                        characterDetails = null
                    }
                }
            }
        }
    }

    /**
     * Fetches planet details by the given [id].
     * Updates [planetState] and manages general UI state flags.
     * @param id The ID of the planet to fetch.
     */
    fun getPlanet(id: Int) {
        viewModelScope.launch {
            _planetState.value = PlanetState.Loading
            isLoading.value = true
            isError.value = false
            isSuccess.value = false

            if (networkStatus.value == ConnectivityObserver.Status.Unavailable) {
                _planetState.value = PlanetState.Error("No Internet Connection")
                return@launch
            }
            try {
                val response = repository.getPlanetById(id)
                if (response.isSuccessful && response.body() != null) {
                    _planetState.value = PlanetState.Success(response.body()!!)
                } else {
                    _planetState.value =
                        PlanetState.Error(response.message().takeIf { it.isNotEmpty() }
                            ?: "Failed to fetch planet")
                }
            } catch (e: Exception) {
                _planetState.value = PlanetState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    /**
     * Observes the [planetState] Flow and updates general UI state flags and [planetDetails].
     */
    private fun observePlanet() {
        planetObserverJob?.cancel()
        planetObserverJob = viewModelScope.launch {
            planetState.collect { state ->
                isLoading.value = state is PlanetState.Loading
                isError.value = state is PlanetState.Error
                isSuccess.value = state is PlanetState.Success

                when (state) {
                    is PlanetState.Success -> {
                        planetDetails = state.planet
                        errorMessage.value = ""
                    }

                    is PlanetState.Error -> {
                        errorMessage.value = state.message
                        planetDetails = null
                    }

                    is PlanetState.Loading -> {
                        errorMessage.value = ""
                        planetDetails = null
                    }
                }
            }
        }
    }

    /**
     * Fetches ship (vehicle) details by the given [id].
     * Updates [vehicleState] and manages general UI state flags.
     * @param id The ID of the ship/vehicle to fetch.
     */
    fun getShip(id: Int) {
        viewModelScope.launch {
            _vehicleState.value = VehicleState.Loading
            isLoading.value = true
            isError.value = false
            isSuccess.value = false

            if (networkStatus.value == ConnectivityObserver.Status.Unavailable) {
                _vehicleState.value = VehicleState.Error("No Internet Connection")
                return@launch
            }
            try {
                val response =
                    repository.getStarshipById(id) // Assuming this is the correct repository method
                if (response.isSuccessful && response.body() != null) {
                    _vehicleState.value = VehicleState.Success(response.body()!!)
                } else {
                    _vehicleState.value =
                        VehicleState.Error(response.message().takeIf { it.isNotEmpty() }
                            ?: "Failed to fetch ship")
                }
            } catch (e: Exception) {
                _vehicleState.value = VehicleState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    /**
     * Observes the [vehicleState] Flow and updates general UI state flags and [vehicleDetails].
     */
    private fun observeShip() {
        shipObserverJob?.cancel()
        shipObserverJob = viewModelScope.launch {
            vehicleState.collect { state ->
                isLoading.value = state is VehicleState.Loading
                isError.value = state is VehicleState.Error
                isSuccess.value = state is VehicleState.Success

                when (state) {
                    is VehicleState.Success -> {
                        vehicleDetails = state.vehicle
                        errorMessage.value = ""
                    }

                    is VehicleState.Error -> {
                        errorMessage.value = state.message
                        vehicleDetails = null
                    }

                    is VehicleState.Loading -> {
                        errorMessage.value = ""
                        vehicleDetails = null
                    }
                }
            }
        }
    }

    /**
     * It's good practice to cancel any ongoing jobs when the ViewModel is cleared,
     * though viewModelScope usually handles this for coroutines launched within it.
     * Explicit cancellation of observer jobs might be redundant if they are tied to viewModelScope
     * and StateFlows handle cleanup correctly.
     */
    override fun onCleared() {
        super.onCleared()
        characterObserverJob?.cancel()
        planetObserverJob?.cancel()
        shipObserverJob?.cancel()
    }
}
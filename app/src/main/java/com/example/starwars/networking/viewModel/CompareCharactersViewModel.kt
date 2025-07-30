package com.example.starwars.networking.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.koin.StarWarsRepository
import com.example.starwars.networking.model.characters.CharactersItem
import com.example.starwars.utils.network.ConnectivityObserver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CompareCharactersViewModel(
    private val repository: StarWarsRepository,
    connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _characterState =
        MutableStateFlow<DetailsViewModel.CharacterState>(DetailsViewModel.CharacterState.Loading) // Initial state can be Loading or Idle

    val characterState: StateFlow<DetailsViewModel.CharacterState> = _characterState.asStateFlow()

    /** Indicates if any data loading operation is currently in progress. */
    var isLoading = mutableStateOf(false)

    /** Indicates if the last data loading operation was successful. */
    var isSuccess = mutableStateOf(false)

    /** Indicates if an error occurred during the last data loading operation. */
    var isError = mutableStateOf(false)

    /** Stores the error message if an error occurs. */
    var errorMessage = mutableStateOf("")

    /** Holds the successfully fetched [CharactersItem] data. Null if not fetched or an error occurred. */
    var characterDetails1: CharactersItem? = null

    /** Holds the successfully fetched [CharactersItem] data. Null if not fetched or an error occurred. */
    var characterDetails2: CharactersItem? = null

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

    /**
     * Represents the possible states for character detail fetching.
     */
    sealed class CharacterState {
        /** Indicates that character data is currently being loaded. */
        object Loading : CharacterState() // Added Loading state

        /** Indicates that character data was successfully fetched. */
        data class Success(val character: CharactersItem) : CharacterState()

        /** Indicates that an error occurred while fetching character data. */
        data class Error(val message: String) : CharacterState()
    }

    fun getCharacter(id: Int?) {
        viewModelScope.launch {
            _characterState.value = DetailsViewModel.CharacterState.Loading
            isLoading.value = true
            isError.value = false
            isSuccess.value = false

            if (networkStatus.value == ConnectivityObserver.Status.Unavailable) {
                _characterState.value = DetailsViewModel.CharacterState.Error("No Internet Connection")
                return@launch
            }

            try {
                val response = repository.getCharacterById(id ?: 1)
                if (response.isSuccessful && response.body() != null) {
                    _characterState.value = DetailsViewModel.CharacterState.Success(response.body()!!)
                } else {
                    _characterState.value =
                        DetailsViewModel.CharacterState.Error(response.message().takeIf { it.isNotEmpty() }
                            ?: "Failed to fetch character")
                }
            } catch (e: Exception) {
                _characterState.value =
                    DetailsViewModel.CharacterState.Error(e.message ?: "An unknown error occurred")
            } finally {
                observeCharacter()
            }
        }
    }


    private fun observeCharacter() {
        viewModelScope.launch {
            characterState.collect { state ->
                when (state) {
                    is DetailsViewModel.CharacterState.Success -> {
                        if (characterDetails1 == null) {
                            characterDetails1 = state.character
                        } else {
                            characterDetails2 = state.character
                        }
                        errorMessage.value = ""
                        if (characterDetails1 != null && characterDetails2 != null) {
                            isSuccess.value = true
                        }
                        isLoading.value = false
                        isError.value = false
                    }

                    is DetailsViewModel.CharacterState.Error -> {
                        errorMessage.value = state.message
                        isSuccess.value = false
                        isLoading.value = false
                        isError.value = true
                    }

                    is DetailsViewModel.CharacterState.Loading -> {
                        errorMessage.value = ""
                        isSuccess.value = false
                        isLoading.value = true
                        isError.value = false
                    }
                }
            }
        }
    }
}
package com.example.shoppermova.ui.screens.rides

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppermova.repository.RideRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RidesViewModel (
    private val rideRepository: RideRepository
) : ViewModel() {
    private val _state = MutableStateFlow(RidesState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val _showToast = MutableSharedFlow<String>()
    val showToastMessage = _showToast.asSharedFlow()

    fun onAction(action : RidesAction) {
        when (action) {
            is RidesAction.OnShowToast -> {
                _showToast.tryEmit(action.message)
            }

            is RidesAction.OnChangeSearchDriver -> {
                _state.update {
                    it.copy(
                        searchDriver = action.searchDriver,
                        driversOptions = driversOptions.filter { driverOption ->
                            if (it.searchDriver.isNotEmpty()) {
                                driverOption.name.contains(action.searchDriver, ignoreCase = true)
                            } else {
                                true
                            }
                        }
                    )
                }
            }

            is RidesAction.OnChangeIsSearchOptionsActive -> {
                _state.update {
                    it.copy(
                        isSearchOptionsActive = action.isSearchOptionsActive
                    )
                }
            }

            is RidesAction.OnFilterByDriver -> {
                _state.update {
                    it.copy(
                        searchDriver = action.driver.name,
                        isSearchOptionsActive = false,
                        rides = emptyList()
                    )
                }

                this.onGetRides(action.driver.id)
            }
        }
    }

    fun onGetRides(driverId: Int? = null) {
        viewModelScope.launch {
            val response = rideRepository.getRides(driverId)

            if (
                response?.error_description != null
            ) {
                onAction(RidesAction.OnShowToast("Erro interno ao chamar a API: \n${response.error_description}"))
            }

            if (response != null) {
                _state.update {
                    it.copy(
                        rides = response.rides
                    )
                }
            }
        }
    }
}


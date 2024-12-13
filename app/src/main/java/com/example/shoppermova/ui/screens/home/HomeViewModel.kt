package com.example.shoppermova.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.shoppermova.models.http.Driver
import com.example.shoppermova.repository.ConfirmRide
import com.example.shoppermova.repository.GetRideEstimate
import com.example.shoppermova.repository.RideRepository
import com.example.shoppermova.ui.navigation.Destinations
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel (
    private val rideRepository: RideRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val mapDefaultLocation = LatLng(-23.5699858, -46.6525985)
    private val mapDefaultZoom = 10f

    private val boundsBuilder = LatLngBounds.builder()

    private val _showToast = MutableSharedFlow<String>()
    val showToastMessage = _showToast.asSharedFlow()

    fun onAction(action : HomeAction) {
        when (action) {
            is HomeAction.OnChangeFromText -> {
                _state.update {
                    it.copy(
                        fromText = action.text,
                    )
                }
                this.onChangeLocations()
            }

            is HomeAction.OnChangeToText -> {
                _state.update {
                    it.copy(
                        toText = action.text,
                    )
                }
                this.onChangeLocations()
            }

            is HomeAction.OnChangeSelectInput -> {
                _state.update {
                    it.copy(
                        selectedInput = action.input,
                    )
                }
                this.onChangeLocations()
            }

            is HomeAction.OnSelectDriverOption -> {
                _state.update {
                    it.copy(
                        selectedRideOption = action.rideOption
                    )
                }
            }

            is HomeAction.OnChangeBottomSheetHeight -> {
                _state.update {
                    it.copy(
                        bottomSheetHeight = action.height
                    )
                }
            }

            is HomeAction.OnDismissSelectDriver -> {
                _state.update {
                    it.copy(
                        pageStep = PageStep.Map,
                        selectedRideOption = null,
                        rideEstimate = null
                    )
                }
            }

            is HomeAction.OnChangePageStep -> {
                _state.update {
                    it.copy(
                        pageStep = action.pageStep
                    )
                }
            }

            is HomeAction.OnCleanInputs -> {
                _state.update {
                    it.copy(
                        fromText = "",
                        toText = "",
                        searchedLocations = emptyList(),
                        ridePoints = emptyList(),
                    )
                }
            }

            is HomeAction.OnInputFocusRequest -> {
                _state.value.inputFocusRequester.let {
                    when (action.input) {
                        Inputs.From -> it.from.requestFocus()
                        Inputs.To -> it.to.requestFocus()
                    }
                }
            }

            is HomeAction.OnShowToast -> {
                viewModelScope.launch {
                    _showToast.emit(action.message)
                }
            }

            is HomeAction.OnConfirmRide -> {
                viewModelScope.launch {
                    onConfirmRide(action.navController)
                }
            }
        }
    }

    private suspend fun onConfirmRide(navController: NavController) {
        _state.update {
            it.copy(
                isConfirmingRide = true
            )
        }
        rideRepository.confirmRide(
            ConfirmRide(
                from = state.value.fromText,
                to = state.value.toText,
                distance = state.value.rideEstimate!!.distance,
                duration = state.value.rideEstimate!!.duration,
                driver = Driver(
                    id = state.value.selectedRideOption!!.id,
                    name = state.value.selectedRideOption!!.name
                ),
                value = state.value.selectedRideOption!!.value
            )
        )
        _state.update {
            it.copy(
                isConfirmingRide = true
            )
        }
        navController.navigate(Destinations.RIDES.route)
    }

    private fun onChangeLocations() {
        val selectedText = if (state.value.selectedInput == "from") state.value.fromText else state.value.toText
        _state.update {
            it.copy(
                searchedLocations = it.locations.filter { location ->
                    if (if (it.selectedInput == "from") it.fromText.length > 2 else it.toText.length > 2) {
                        location.contains(selectedText, ignoreCase = true)
                    } else {
                        true
                    }
                }
            )
        }

        viewModelScope.launch {
            onLocationsChosen()
        }
    }

    private suspend fun onLocationsChosen() {
        if (
            state.value.fromText.isNotEmpty()
            && state.value.toText.isNotEmpty()
        ) {
            this.onAction(HomeAction.OnChangePageStep(PageStep.ChooseDriver))

            val response = rideRepository.getRideEstimate(
                GetRideEstimate(
                    from = state.value.fromText,
                    to = state.value.toText
                )
            )

            if (
                response?.error_code != null
                || response?.origin?.latitude == 0.0
            ) {
                this.onAction(HomeAction.OnChangePageStep(PageStep.Map))
                this.onAction(HomeAction.OnCleanInputs())

                if (response.error_description != null) this.onAction(HomeAction.OnShowToast(response.error_description)) else this.onAction(HomeAction.OnShowToast("Não foi possível encontrar motoristas disponíveis para essa rota"))

                return
            }

            val ridePoints = response!!.routeResponse?.let { routeResponse ->
                routeResponse.routes[0].legs.flatMap { leg ->
                    leg.steps.map { step ->
                        LatLng(step.startLocation.latLng.latitude, step.startLocation.latLng.longitude)
                    }
                }
            }

            this._state.value.cameraPositionState.move(
                update = CameraUpdateFactory.newLatLngZoom(
                    mapDefaultLocation,
                    mapDefaultZoom
                )
            )

            ridePoints?.forEach { boundsBuilder.include(it) }

            val bounds = boundsBuilder.build()

            this._state.value.cameraPositionState.move(
                update = CameraUpdateFactory.newLatLngBounds(bounds, 120),
            )

            _state.update {
                it.copy(
                    rideEstimate = response,
                    ridePoints = ridePoints,
                )
            }
        }
    }
}
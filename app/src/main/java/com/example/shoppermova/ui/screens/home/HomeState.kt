package com.example.shoppermova.ui.screens.home

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.shoppermova.models.http.GetRideEstimateResponse
import com.example.shoppermova.models.http.RideOption
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState

enum class PageStep {
    Map,
    ChooseDestination,
    ChooseDriver
}

enum class Inputs {
    From,
    To
}

class InputFocusRequester {
    val from = FocusRequester()
    val to = FocusRequester()
}

data class HomeState (
    val locations: List<String> = listOf(
        "Av. Pres. Kenedy, 2385 - Remédios, Osasco - SP, 02675-031",
        "Av. Paulista, 1538 - Bela Vista, São Paulo - SP, 01310-200",
        "Av. Thomas Edison, 365 - Barra Funda, São Paulo - SP, 01140-000",
        "Av. Brasil, 2033 - Jardim America, São Paulo - SP, 01431-001",
    ),

    val fromText: String = "",
    val toText: String = "",
    val selectedInput: String = "",
    val searchedLocations: List<String> = emptyList(),

    val pageStep: PageStep = PageStep.Map,

    val cameraPositionState: CameraPositionState = CameraPositionState(
        position = CameraPosition.fromLatLngZoom(LatLng(-23.5699858, -46.6525985), 10f)
    ),

    val rideEstimate: GetRideEstimateResponse? = null,
    val ridePoints: List<LatLng>? = emptyList(),
    val selectedRideOption: RideOption? = null,

    val bottomSheetHeight: Dp = 0.dp,

    val inputFocusRequester: InputFocusRequester = InputFocusRequester(),

    val isConfirmingRide: Boolean = false,
)
package com.example.shoppermova.ui.screens.home

import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import com.example.shoppermova.models.http.RideOption

sealed interface HomeAction {
    data class OnChangeFromText(val text: String) : HomeAction
    data class OnChangeToText(val text: String) : HomeAction
    class OnCleanInputs() : HomeAction
    data class OnChangeSelectInput(val input: String) : HomeAction
    data class OnSelectDriverOption(val rideOption: RideOption) : HomeAction
    class OnDismissSelectDriver() : HomeAction
    data class OnChangePageStep(val pageStep: PageStep) : HomeAction

    data class OnShowToast(val message: String) : HomeAction

    data class OnConfirmRide(val navController: NavController) : HomeAction

    data class OnInputFocusRequest(val input: Inputs) : HomeAction

    data class  OnChangeBottomSheetHeight(val height: Dp) : HomeAction
}
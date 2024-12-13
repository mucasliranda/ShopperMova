package com.example.shoppermova.ui.screens.rides

import com.example.shoppermova.models.http.Driver

sealed interface RidesAction {
    data class OnShowToast(val message: String) : RidesAction
    data class OnChangeSearchDriver(val searchDriver: String) : RidesAction
    data class OnChangeIsSearchOptionsActive(val isSearchOptionsActive: Boolean) : RidesAction
    data class OnFilterByDriver(val driver: Driver) : RidesAction
}
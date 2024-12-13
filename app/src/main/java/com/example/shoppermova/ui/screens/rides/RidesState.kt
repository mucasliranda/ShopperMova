package com.example.shoppermova.ui.screens.rides

import com.example.shoppermova.models.http.Driver
import com.example.shoppermova.models.http.Ride

val driversOptions = listOf(
    Driver(1, "Homer Simpson"),
    Driver(2, "Dominic Toretto"),
    Driver(3, "James Bond"),
)

data class RidesState (
    val rides : List<Ride> = emptyList(),
    val isSearchOptionsActive: Boolean = false,
    val searchDriver: String = "",
    val driversOptions: List<Driver> = listOf(
        Driver(1, "Homer Simpson"),
        Driver(2, "Dominic Toretto"),
        Driver(3, "James Bond"),
    ),
)
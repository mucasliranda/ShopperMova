package com.example.shoppermova.ui.navigation

sealed class Destinations(
    val route: String
) {
    object HOME : Destinations("home")
    object RIDES : Destinations("rides")
}
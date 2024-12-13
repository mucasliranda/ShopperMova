package com.example.shoppermova.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppermova.ui.screens.home.HomeScreen
import com.example.shoppermova.ui.screens.rides.RidesScreen

@Composable
fun RootNavivation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.HOME.route,
    ) {
        composable(
            route = Destinations.HOME.route
        ) {
            HomeScreen(navController = navController)
        }

        composable(
            route = Destinations.RIDES.route
        ) {
            RidesScreen(navController = navController)
        }
    }
}
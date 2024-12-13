package com.example.shoppermova.models.http

import com.google.android.gms.maps.model.LatLng

data class Location (
    val latitude: Double,
    val longitude: Double
) {
    fun toLatLng(): LatLng {
        return LatLng(latitude, longitude)
    }
}

data class RideOption (
    val id: Int,
    val name: String,
    val description: String,
    val vehicle: String,
    val review: RideReview,
    val value: Double
)

data class RideReview (
    val rating: Double,
    val comment: String
)

data class RouteResponse (
    val routes: List<Route>
)

data class NavigationInstruction(
    val maneuver: String,
    val instructions: String
)

data class Step(
    val distanceMeters: Int,
    val staticDuration: String,
    val polyline: Polyline,
    val startLocation: LatLngLocation,
    val endLocation: LatLngLocation,
    val navigationInstruction: NavigationInstruction,
    val localizedValues: LocalizedValues,
    val travelMode: String
)

data class LatLngLocation (
    val latLng: Location
)

data class Leg(
    val distanceMeters: Int,
    val duration: String,
    val staticDuration: String,
    val polyline: Polyline,
    val startLocation: Location,
    val endLocation: Location,
    val steps: List<Step>
)

data class Polyline (
    val encodedPolyline: String
)

data class Viewport (
    val low: Location,
    val high: Location
)

data class TravelAdvisory (
    val nothing: String
)

data class Distance (
    val text: String
)

data class Duration (
    val text: String
)

data class LocalizedValues (
    val distance: Distance,
    val duration: Duration,
    val staticDuration: Duration
)

data class PolylineDetails (
    val nothing: String
)

data class Route (
    val legs: List<Leg>,
    val distanceMeters: Int,
    val duration: String,
    val staticDuration: String,
    val polyline: Polyline,
    val description: String,
    val warnings: List<String>,
    val viewport: Viewport,
    val travelAdvisory: TravelAdvisory,
    val localizedValues: LocalizedValues,
    val routeLabels: List<String>,
    val polylineDetails: PolylineDetails
)

data class GetRideEstimateResponse(
    val origin: Location,
    val destination: Location,
    val distance: Double,
    val duration: String,
    val options: List<RideOption>?,
    val routeResponse: RouteResponse?,
    val error_code: String?,
    val error_description: String?,
)

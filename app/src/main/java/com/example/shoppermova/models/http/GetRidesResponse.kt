package com.example.shoppermova.models.http

data class Ride(
    val id: Int,
    val date: String,
    val origin: String,
    val destination: String,
    val distance: Double,
    val duration: String,
    val driver: Driver,
    val value: Double,
)

data class GetRidesResponse (
    val customer_id: String,
    val rides: List<Ride>,
    val error_code: String?,
    val error_description: String?
)
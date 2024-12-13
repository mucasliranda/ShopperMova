package com.example.shoppermova.models.http

data class GetRideEstimateRequest(
    val customer_id: String,
    val origin: String,
    val destination: String
)

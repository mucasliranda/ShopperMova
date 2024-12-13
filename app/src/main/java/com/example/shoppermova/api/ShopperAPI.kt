package com.example.shoppermova.api

import com.example.shoppermova.models.http.ConfirmRideRequest
import com.example.shoppermova.models.http.ConfirmRideResponse
import com.example.shoppermova.models.http.GetRideEstimateRequest
import com.example.shoppermova.models.http.GetRideEstimateResponse
import com.example.shoppermova.models.http.GetRidesResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface ShopperAPI {
    @POST("/ride/estimate")
    fun getRideEstimate(@Body request: GetRideEstimateRequest): Call<GetRideEstimateResponse>

    @PATCH("/ride/confirm")
    fun confirmRide(@Body request: ConfirmRideRequest): Call<ConfirmRideResponse>

    @GET("/ride/{customer_id}")
    fun getRides(@Path("customer_id") customerId: String, @Query("driver_id") driverId: Int?): Call<GetRidesResponse>
}
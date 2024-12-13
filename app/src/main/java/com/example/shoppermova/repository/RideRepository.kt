package com.example.shoppermova.repository

import android.util.Log
import com.example.shoppermova.api.ShopperAPI
import com.example.shoppermova.models.http.ConfirmRideRequest
import com.example.shoppermova.models.http.ConfirmRideResponse
import com.example.shoppermova.models.http.Driver
import com.example.shoppermova.models.http.GetRideEstimateRequest
import com.example.shoppermova.models.http.GetRideEstimateResponse
import com.example.shoppermova.models.http.GetRidesResponse
import com.example.shoppermova.ui.screens.home.HomeAction
import com.google.gson.Gson
import kotlinx.coroutines.delay
import retrofit2.http.Query

data class GetRideEstimate(
    val from: String,
    val to: String
)

data class ConfirmRide(
    val from: String,
    val to: String,
    val distance: Double,
    val duration: String,
    val driver: Driver,
    val value: Double
)

class RideRepository(
    private val shopperAPI: ShopperAPI
) {
    private val customerId = "CT01"

    suspend fun getRideEstimate(getRideEstimate: GetRideEstimate): GetRideEstimateResponse? {
        val result = shopperAPI.getRideEstimate(
            GetRideEstimateRequest(
                origin = getRideEstimate.from,
                destination = getRideEstimate.to,
                customer_id = customerId
            )
        ).execute()

        delay(2000)

        Log.d("RideRepository", "getRideEstimate: ${result}")

        if (result.isSuccessful) {
            return result.body()
        }

        val errorResponse = Gson().fromJson(result.errorBody()!!.charStream(), GetRideEstimateResponse::class.java)

        Log.d("RideRepository", "getRideEstimate: ${errorResponse}")

        return errorResponse
    }

    suspend fun confirmRide(confirmRide: ConfirmRide): ConfirmRideResponse? {
        Log.d("RideRepository", "confirmRide: ${confirmRide}")

        val result = shopperAPI.confirmRide(
            ConfirmRideRequest(
                customer_id = customerId,
                origin = confirmRide.from,
                destination = confirmRide.to,
                distance = confirmRide.distance,
                duration = confirmRide.duration,
                driver = confirmRide.driver,
                value = confirmRide.value
            )
        ).execute()

        Log.d("RideRepository", "confirmRide: ${result}")

        if (result.isSuccessful) {
            return result.body()
        }

        val errorResponse = Gson().fromJson(result.errorBody()!!.charStream(), ConfirmRideResponse::class.java)

        Log.d("RideRepository", "confirmRide: ${errorResponse}")

        return errorResponse
    }

    suspend fun getRides(driverId: Int? = null): GetRidesResponse? {
        val result = shopperAPI.getRides(customerId, driverId).execute()

        delay(2000)

        Log.d("RideRepository", "getRides: ${result}")

        if (result.isSuccessful) {
            return result.body()
        }

        val errorResponse = Gson().fromJson(result.errorBody()!!.charStream(), GetRidesResponse::class.java)

        Log.d("RideRepository", "getRides: ${errorResponse}")

        return errorResponse
    }
}
package com.example.shoppermova.di

import com.example.shoppermova.api.ShopperAPI
import com.example.shoppermova.repository.RideRepository
import com.example.shoppermova.ui.screens.home.HomeViewModel
import com.example.shoppermova.ui.screens.rides.RidesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
         Retrofit.Builder()
            .baseUrl("https://xd5zl5kk2yltomvw5fb37y3bm40vsyrx.lambda-url.sa-east-1.on.aws/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
             .create(ShopperAPI::class.java)
    }
    single<RideRepository> {
        RideRepository(get())
    }
    viewModel {
        HomeViewModel(get())
    }
    viewModel {
        RidesViewModel(get())
    }
}
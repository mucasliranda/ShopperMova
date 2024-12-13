package com.example.shoppermova

import android.app.Application
import com.example.shoppermova.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ShopperMovaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ShopperMovaApp)
            modules(appModule)
        }
    }
}
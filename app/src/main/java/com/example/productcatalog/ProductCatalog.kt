package com.example.productcatalog

import android.app.Application
import com.example.productcatalog.data.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ProductCatalog: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ProductCatalog)
            modules(appModule)
        }
    }
}
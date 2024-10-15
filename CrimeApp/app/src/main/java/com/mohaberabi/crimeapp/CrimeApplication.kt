package com.mohaberabi.crimeapp

import android.app.Application
import com.mohaberabi.crimeapp.core.di.AppModule
import com.mohaberabi.crimeapp.core.di.DefaultAppModule

class CrimeApplication : Application() {


    companion object {
        lateinit var appModule: AppModule

    }

    override fun onCreate() {
        super.onCreate()
        appModule = DefaultAppModule(this)
    }

}
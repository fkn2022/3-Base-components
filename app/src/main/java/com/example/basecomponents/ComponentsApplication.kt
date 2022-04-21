package com.example.basecomponents

import android.app.Application
import android.util.Log

class ComponentsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("BaseComponents", "I'm an application")
    }
}
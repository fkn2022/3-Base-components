package com.example.basecomponents

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import com.example.basecomponents.utils.Utils

class ComponentsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("BaseComponents", "I'm an application")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Utils.CHANNEL_ID,
                Utils.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )

            channel.description = Utils.CHANNEL_DESC

            val managerCompat = getSystemService(NotificationManager::class.java)
            managerCompat.createNotificationChannel(channel)
        }
    }
}
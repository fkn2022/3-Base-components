package com.example.basecomponents

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.basecomponents.databinding.ActivityMainBinding
import com.example.basecomponents.workers.NotificationWorker
import java.nio.charset.CodingErrorAction.REPLACE
import java.time.Duration
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val NOTIFICATION_ID = 888

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        createNotificationChannel()
    }

    override fun onStart() {
        super.onStart()
        binding.startActivityButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                ).apply {
                    putExtra("EXTRA", "Hi there")

                    /**
                     * Testing with Launch types
                     */
                    addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                }
            )
        }

        binding.createPeriodicButton.setOnClickListener {
            val notificationWork = PeriodicWorkRequestBuilder<NotificationWorker>(16, TimeUnit.MINUTES).build()
            val instanceWorkManager = WorkManager.getInstance(this)
            instanceWorkManager.enqueue(notificationWork)
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Toast.makeText(this, "${intent?.extras?.get("EXTRA")}", Toast.LENGTH_SHORT).show()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
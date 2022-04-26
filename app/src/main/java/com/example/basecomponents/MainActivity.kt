package com.example.basecomponents

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.basecomponents.databinding.ActivityMainBinding
import com.example.basecomponents.utils.Utils
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var layout: View
    private lateinit var binding: ActivityMainBinding
    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        layout = binding.mainLayout
        setContentView(view)
    }

    override fun onStart() {
        super.onStart()
        broadcastReceiver = NetworkStateReceiver()

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

        binding.getContactButton.setOnClickListener {
            onClickRequestPermission(it)
        }

        binding.createPeriodicButton.setOnClickListener {
            periodicNotification()
        }

        binding.turnNetworkBroadcastButton.setOnClickListener {
            networkBroadcast()
        }

        binding.fragmentWithGyroscopeButton.setOnClickListener {
            gyroscopeButton()
        }
    }

    private fun gyroscopeButton() {
        startActivity(
            Intent(this, GyroscopeActivity::class.java)
        )
    }

    private fun networkBroadcast() {
        val sharedPref =
            getSharedPreferences(Utils.CONNECTION_CHECKER_IS_ON, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        if (!sharedPref.getBoolean(Utils.CONNECTION_CHECKER_IS_ON, false)) {
            editor.putBoolean(Utils.CONNECTION_CHECKER_IS_ON, true)

            registerReceiver(
                broadcastReceiver,
//                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
                IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
            )
        } else {
            editor.putBoolean(Utils.CONNECTION_CHECKER_IS_ON, false)

            try {
                unregisterReceiver(broadcastReceiver)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        editor.apply()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.i("Permission: ", "Granted")
            } else {
                Log.i("Permission: ", "Denied")
            }
        }

    private fun onClickRequestPermission(view: View) {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED -> {
                layout.showSnackbar(
                    view,
                    getString(R.string.permission_granted),
                    Snackbar.LENGTH_SHORT,
                    null
                ) {}
                startActivity(
                    Intent(
                        this,
                        ContactsActivity::class.java
                    )
                )
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_CONTACTS
            ) -> {
                layout.showSnackbar(
                    view,
                    getString(R.string.permission_required),
                    Snackbar.LENGTH_SHORT,
                    getString(R.string.ok)
                ) {
                    requestPermissionLauncher.launch(
                        Manifest.permission.READ_CONTACTS
                    )
                }
                startActivity(
                    Intent(
                        this,
                        ContactsActivity::class.java
                    )
                )
            }

            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.READ_CONTACTS
                )
            }
        }
    }

    private fun periodicNotification() {
        val sharedPref =
            getSharedPreferences(Utils.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        if (!sharedPref.getBoolean(Utils.NOTIFICATION_BUTTON_IS_ON, false)) {
            val myWorkRequest =
                PeriodicWorkRequestBuilder<NotificationWorker>(15, TimeUnit.MINUTES)
                    .addTag(applicationContext.getString(R.string.notification_tag))
                    .build()

            WorkManager.getInstance(applicationContext).enqueue(myWorkRequest)

            editor.putBoolean(Utils.NOTIFICATION_BUTTON_IS_ON, true)
        } else {
            WorkManager.getInstance(applicationContext)
                .cancelAllWorkByTag(getString(R.string.notification_tag))

            editor.putBoolean(Utils.NOTIFICATION_BUTTON_IS_ON, false)
        }
        editor.apply()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Toast.makeText(this, "${intent?.extras?.get("EXTRA")}", Toast.LENGTH_SHORT).show()
    }
}

fun View.showSnackbar(
    view: View,
    msg: String,
    length: Int,
    actionMessage: CharSequence?,
    action: (View) -> Unit
) {
    val snackbar = Snackbar.make(view, msg, length)
    if (actionMessage != null) {
        snackbar.setAction(actionMessage) {
            action(this)
        }.show()
    } else {
        snackbar.show()
    }
}
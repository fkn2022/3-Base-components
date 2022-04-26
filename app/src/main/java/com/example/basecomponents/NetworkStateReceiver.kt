package com.example.basecomponents

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast


class NetworkStateReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1?.action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            if (checkInternetConnection(p0!!)) {
                Toast.makeText(p0, "Connection is turned on", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(p0, "Connection is turned off", Toast.LENGTH_LONG).show()
            }
        }
    }
}

fun checkInternetConnection(context: Context): Boolean {
    val hasInternet: Boolean

    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        hasInternet = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        hasInternet = try {
            if (connectivityManager.activeNetworkInfo == null) {
                false
            } else {
                connectivityManager.activeNetworkInfo?.isConnected!!
            }
        } catch (e: Exception) {
            false
        }
    }
    return hasInternet
}



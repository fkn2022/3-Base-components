package com.example.basecomponents

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast


class NetworkStateReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {

        val connectivityManager =
            p0?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork
            val actNw = connectivityManager.getNetworkCapabilities(nw)
            if (nw == null || actNw == null) {
                Toast.makeText(p0, "Connection is ", Toast.LENGTH_LONG).show()
                return
            }
            when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> Toast.makeText(p0, "Connection is ", Toast.LENGTH_LONG).show()
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> Toast.makeText(p0, "Connection is ", Toast.LENGTH_LONG).show()
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> Toast.makeText(p0, "Connection is ", Toast.LENGTH_LONG).show()
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> Toast.makeText(p0, "Connection is ", Toast.LENGTH_LONG).show()
                else -> Toast.makeText(p0, "Connection is ", Toast.LENGTH_LONG).show()
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo
            nwInfo?.isConnected
        }
    }
}

//if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//    val connectivityManager =
//        p0?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//    val networkInfo = connectivityManager.activeNetwork
//    if(networkInfo!=null) {
//        Toast.makeText(p0, "Connection is turned on", Toast.LENGTH_LONG).show()
//    }else{
//        Toast.makeText(p0, "Connection is turned off", Toast.LENGTH_LONG).show()
//    }
//}

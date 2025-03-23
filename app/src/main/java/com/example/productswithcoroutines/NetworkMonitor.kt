package com.example.productswithcoroutines

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities


class NetworkMonitor(context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isNetworkAvailable(): Boolean {
        val capabilities: NetworkCapabilities? =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork);
        var isAvailable = false;

        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                isAvailable = true;
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                isAvailable = true;
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                isAvailable = true;
            }
        }
        return isAvailable
    }

    fun registerNetworkCallback(networkCallback: ConnectivityManager.NetworkCallback) {
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    fun unregisterNetworkCallback(networkCallback: ConnectivityManager.NetworkCallback) {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
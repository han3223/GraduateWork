package com.example.documentsearch.patterns

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

class InternetConnectionFactory {
    fun internetConnectionCheck(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnectedOrConnecting

    }
}
package com.example.documentsearch

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.StrictMode
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.caverock.androidsvg.BuildConfig
import com.example.documentsearch.navbar.Navigator
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Suppress("DEPRECATION")
@SuppressLint("CoroutineCreationDuringComposition", "MutableCollectionMutableState")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        if (BuildConfig.DEBUG) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        setContent {
            val systemUiController = rememberSystemUiController()
            SideEffect { systemUiController.setStatusBarColor(color = Color.Transparent) }

            Navigator().Content()
        }
    }
}

@file:OptIn(ExperimentalAnimationApi::class)

package com.example.documentsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.documentsearch.header.documentScreen.Header
import com.example.documentsearch.header.documentScreen.icon
import com.example.documentsearch.navbar.Navbar
import com.example.documentsearch.navbar.NavigationItem
import com.example.documentsearch.screens.addUser.AddUserScreen
import com.example.documentsearch.screens.document.DocumentScreen
import com.example.documentsearch.screens.messenger.MessengerScreen
import com.example.documentsearch.screens.profile.ProfileScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.setStatusBarColor(color = Color.Transparent)
            }

            val navController = rememberNavController()
            Scaffold(
                modifier = Modifier.fillMaxSize().pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            if (icon.intValue != R.drawable.active_filter)
                                icon.intValue = R.drawable.header
                        },
                    )
                },
                content = { padding ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .zIndex(100f)
                                .align(Alignment.TopCenter)
                        ) { Header() }
                        Box(
                            modifier = Modifier
                                .padding(top = 140.dp)
                                .zIndex(0f)
                                .fillMaxHeight()
                                .align(Alignment.Center)
                        ) {
                            NavHost(
                                navController,
                                startDestination = NavigationItem.Documents.route
                            ) {
                                composable(NavigationItem.Documents.route) {
                                    DocumentScreen()
                                }

                                composable(NavigationItem.Messenger.route) {
                                    MessengerScreen()
                                }

                                composable(NavigationItem.AddUser.route) {
                                    AddUserScreen()
                                }

                                composable(NavigationItem.Profile.route) {
                                    ProfileScreen()
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .zIndex(100f)
                                .align(Alignment.BottomCenter)
                        ) { Navbar(navController) }
                    }
                },
            )
        }
    }
}

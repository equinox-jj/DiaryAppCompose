package com.diaryappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.diaryappcompose.navigation.Destination
import com.diaryappcompose.navigation.SetupNavGraph
import com.diaryappcompose.ui.theme.DiaryAppComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            DiaryAppComposeTheme {
                val navController = rememberNavController()

                SetupNavGraph(
                    startDestination = Destination.Authentication.route,
                    navController = navController
                )
            }
        }
    }
}
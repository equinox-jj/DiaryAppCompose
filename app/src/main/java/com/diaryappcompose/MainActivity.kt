package com.diaryappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.diaryappcompose.navigation.Routes
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
                    startDestination = Routes.Authentication.route,
                    navController = navController
                )
            }
        }
    }
}
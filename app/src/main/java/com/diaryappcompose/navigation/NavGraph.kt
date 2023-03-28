package com.diaryappcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.diaryappcompose.utils.Constants.WRITE_SCREEN_ARGUMENT

@Composable
fun SetupNavGraph(
    startDestination: String,
    navController: NavHostController
) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        authenticationRoute()
        homeRoute()
        writeRoute()
    }
}

fun NavGraphBuilder.authenticationRoute() {
    composable(route = Routes.Authentication.route) {

    }
}

fun NavGraphBuilder.homeRoute() {
    composable(route = Routes.Home.route) {

    }
}

fun NavGraphBuilder.writeRoute() {
    composable(
        route = Routes.Write.route,
        arguments = listOf(navArgument(name = WRITE_SCREEN_ARGUMENT) {
            type = NavType.StringType
            nullable = true
        })
    ) {

    }
}
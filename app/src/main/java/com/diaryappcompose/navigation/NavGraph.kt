package com.diaryappcompose.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.diaryappcompose.presentation.screens.auth.AuthenticationScreen
import com.diaryappcompose.presentation.screens.auth.AuthenticationViewModel
import com.diaryappcompose.utils.Constants.WRITE_SCREEN_ARGUMENT
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState

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
    composable(route = Destination.Authentication.route) {
        val viewModel: AuthenticationViewModel = viewModel()
        val loadingState = viewModel.loadingState.collectAsStateWithLifecycle().value.loadingState
        val oneTapState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()

        AuthenticationScreen(
            loadingState = loadingState,
            oneTapState = oneTapState,
            messageBarState = messageBarState,
            onButtonClicked = {
                oneTapState.open()
                viewModel.setLoading(true)
            },
            onTokenIdReceived = { tokenId ->
                viewModel.signInWithMongoAtlas(
                    tokenId = tokenId,
                    onSuccess = {
                        if (it) {
                            messageBarState.addSuccess("Successfully Authenticated")
                            viewModel.setLoading(false)
                        }
                    },
                    onError = { exception ->
                        messageBarState.addError(Exception(exception))
                        viewModel.setLoading(false)
                    },
                )
            },
            onDialogDismiss = { message ->
                messageBarState.addError(Exception(message))
            },
        )
    }
}

fun NavGraphBuilder.homeRoute() {
    composable(route = Destination.Home.route) {

    }
}

fun NavGraphBuilder.writeRoute() {
    composable(
        route = Destination.Write.route,
        arguments = listOf(navArgument(name = WRITE_SCREEN_ARGUMENT) {
            type = NavType.StringType
            nullable = true
        })
    ) {

    }
}
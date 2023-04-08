package com.diaryappcompose.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.diaryappcompose.utils.Constants.APP_ID
import com.diaryappcompose.utils.Constants.WRITE_SCREEN_ARGUMENT
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SetupNavGraph(
    startDestination: String,
    navController: NavHostController
) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        authenticationRoute(
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Destination.Home.route)
            },
        )
        homeRoute()
        writeRoute()
    }
}

fun NavGraphBuilder.authenticationRoute(
    navigateToHome: () -> Unit,
) {
    composable(route = Destination.Authentication.route) {
        val viewModel: AuthenticationViewModel = viewModel()
        val loadingState = viewModel.loadingState.collectAsStateWithLifecycle().value.loadingState
        val authenticated = viewModel.authenticated.collectAsStateWithLifecycle().value.authenticated
        val oneTapState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()

        AuthenticationScreen(
            authenticated = authenticated,
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
                        messageBarState.addSuccess("Successfully Authenticated")
                        viewModel.setLoading(false)
                    },
                    onError = { exception ->
                        messageBarState.addError(Exception(exception))
                        viewModel.setLoading(false)
                    },
                )
            },
            onDialogDismiss = { message ->
                messageBarState.addError(Exception(message))
                viewModel.setLoading(false)
            },
            navigateToHome = navigateToHome,
        )
    }
}

fun NavGraphBuilder.homeRoute() {
    composable(route = Destination.Home.route) {
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = {
                    scope.launch(Dispatchers.IO) {
                        App.create(APP_ID).currentUser?.logOut()
                    }
                },
            ) {
                Text(text = "Logout")
            }
        }
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
package com.diaryappcompose.presentation.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diaryappcompose.utils.Constants.APP_ID
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthenticationViewModel : ViewModel() {

    private var _authenticated = MutableStateFlow(AuthenticationState())
    var authenticated = _authenticated.asStateFlow()

    private var _loadingState = MutableStateFlow(AuthenticationState())
    var loadingState = _loadingState.asStateFlow()

    fun setLoading(loadingState: Boolean) {
        _loadingState.update { it.copy(loadingState = loadingState) }
    }

    fun signInWithMongoAtlas(
        tokenId: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    App.create(APP_ID).login(
                        Credentials.jwt(tokenId)
                    ).loggedIn
                }
                withContext(Dispatchers.Main) {
                    if (result) {
                        onSuccess()
                        delay(600)
                        _authenticated.update { it.copy(authenticated = true) }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e)
                }
            }
        }
    }
}

data class AuthenticationState(
    val loadingState: Boolean = false,
    val authenticated: Boolean = false,
)
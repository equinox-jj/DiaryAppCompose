package com.diaryappcompose.navigation

import com.diaryappcompose.utils.Constants.WRITE_SCREEN_ARGUMENT

sealed class Destination(val route: String) {
    object Authentication : Destination("auth_screen")
    object Home : Destination("home_screen")
    object Write : Destination("write_screen?$WRITE_SCREEN_ARGUMENT={$WRITE_SCREEN_ARGUMENT}") {
        fun passDiaryId(diaryId: String) = "write_screen?$WRITE_SCREEN_ARGUMENT=$diaryId"
    }
}

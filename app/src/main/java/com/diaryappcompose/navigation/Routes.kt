package com.diaryappcompose.navigation

import com.diaryappcompose.utils.Constants.WRITE_SCREEN_ARGUMENT

sealed class Routes(val route: String) {
    object Authentication : Routes("auth_screen")
    object Home : Routes("home_screen")
    object Write : Routes("write_screen?$WRITE_SCREEN_ARGUMENT={$WRITE_SCREEN_ARGUMENT}") {
        fun passDiaryId(diaryId: String) = "write_screen?$WRITE_SCREEN_ARGUMENT=$diaryId"
    }
}

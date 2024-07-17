package com.vkartik.myspace.ui.presentation

enum class Screens(val route: String) {
    SIGN_IN("sign_in"), HOME("home")
}

enum class SubScreens(val route: String) {
    DEFAULT("home"), EXPENSES("expenses"), RECORD_TRANSACTIONS("record transactions")
}
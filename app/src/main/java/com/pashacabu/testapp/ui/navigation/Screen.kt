package com.pashacabu.testapp.ui.navigation

sealed class Screen(rout: String) {
    object List: Screen(LIST)
    object Details: Screen(DETAILS)

 companion object {
     const val LIST = "list"
     const val DETAILS = "details"

 }

}


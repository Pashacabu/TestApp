package com.pashacabu.testapp.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pashacabu.testapp.ui.movieslist.MoviesList

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navController,
        startDestination = Screen.LIST,
        modifier = modifier
    ) {

        composable(Screen.LIST){
            MoviesList(modifier.fillMaxSize())
        }

    }
    
}
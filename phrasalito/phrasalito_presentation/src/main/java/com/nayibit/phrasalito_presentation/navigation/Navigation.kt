package com.nayibit.phrasalito_presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nayibit.phrasalito_presentation.homeScreen.HomeScreen
import kotlinx.serialization.Serializable


//Argumentos

@Serializable
object HomeScreen

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeScreen
    ) {

        composable<HomeScreen> {
           HomeScreen()
        }


    }
}
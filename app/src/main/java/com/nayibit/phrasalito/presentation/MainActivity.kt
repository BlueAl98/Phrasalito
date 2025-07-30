package com.nayibit.phrasalito.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nayibit.phrasalito.presentation.homeScreen.HomeScreen
import com.nayibit.phrasalito.presentation.navigation.Navigation
import com.nayibit.phrasalito.presentation.ui.theme.BaseProyectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BaseProyectTheme {
                HomeScreen()
            }
        }
    }
}


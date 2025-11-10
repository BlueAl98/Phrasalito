package com.nayibit.phrasalito_presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.nayibit.phrasalito_presentation.navigation.Navigation
import com.nayibit.phrasalito_presentation.ui.theme.PhrasalitoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            PhrasalitoTheme {

             Surface(modifier = Modifier
                       .fillMaxSize()
                       .windowInsetsPadding(WindowInsets.systemBars), color = MaterialTheme.colorScheme.background) {
                    Navigation()
              }
          }
        }
    }
}


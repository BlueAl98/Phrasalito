package com.nayibit.phrasalito

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.nayibit.phrasalito.navigation.Navigation
import com.nayibit.utils.ui.theme.PhrasalitoTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.graphics.toColorInt

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bgColor = "#120B32".toColorInt()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(bgColor),
            navigationBarStyle = SystemBarStyle.dark(bgColor)
        )
        // Android 15 ignores the scrim color in SystemBarStyle and forces transparent bars.
        // Setting the window background makes that same color show through the transparent bars.
        window.decorView.setBackgroundColor(bgColor)

        setContent {
           PhrasalitoTheme {
             Surface(
                 modifier = Modifier
                     .fillMaxSize()
                     .windowInsetsPadding(WindowInsets.systemBars),
                 color = MaterialTheme.colorScheme.background
             ) {
                    Navigation()
              }
         }
        }
    }
}


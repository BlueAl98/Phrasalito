package com.nayibit.phrasalito_presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.work.OneTimeWorkRequestBuilder
import com.nayibit.phrasalito_presentation.navigation.Navigation
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckScreen
import com.nayibit.phrasalito_presentation.ui.theme.PhrasalitoTheme
import com.nayibit.phrasalito_presentation.workers.RandomPhraseWorker
import com.nayibit.phrasalito_presentation.workers.helpers.RandomPhraseWorkerScheduler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RandomPhraseWorkerScheduler.start(this)

        enableEdgeToEdge()
        setContent {
            PhrasalitoTheme {
                    Navigation()
                }
        }
    }
}


package com.nayibit.phrasalito_presentation.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nayibit.phrasalito_domain.useCases.phrases.RandomPhraseUseCase
import com.nayibit.phrasalito_presentation.utils.NotificationBuilder
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class RandomPhraseWorker  @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val useCaseRandomPhrase: RandomPhraseUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val randomPhrase = useCaseRandomPhrase()

        if (randomPhrase != null) {
            Log.d("RandomPhraseWorker", "Random Phrase: $randomPhrase")
            NotificationBuilder.showNotification(applicationContext, randomPhrase.targetLanguage, randomPhrase.translation)
        }

        return Result.success()
    }

}
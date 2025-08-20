package com.nayibit.phrasalito_presentation.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nayibit.phrasalito_domain.useCases.phrases.GetAllPhrasesUseCase
import com.nayibit.phrasalito_domain.useCases.phrases.RandomPhraseUseCase
import com.nayibit.phrasalito_presentation.utils.NotificationBuilder
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class RandomPhraseWorker  @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val useCaseRandomPhrase: RandomPhraseUseCase)
    : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val randomPhrase = useCaseRandomPhrase()

        return if (randomPhrase != null) {
            NotificationBuilder.showNotification(applicationContext, randomPhrase.targetLanguage, randomPhrase.translation)
            Result.success()
        } else {
            Result.success() // nothing to notify
        }
    }

}
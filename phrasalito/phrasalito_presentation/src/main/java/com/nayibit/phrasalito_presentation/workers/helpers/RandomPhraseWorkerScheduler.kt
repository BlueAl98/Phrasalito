package com.nayibit.phrasalito_presentation.workers.helpers

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.nayibit.phrasalito_presentation.workers.RandomPhraseWorker
import java.util.concurrent.TimeUnit

object RandomPhraseWorkerScheduler {
    fun start(context: Context) {

     val request = PeriodicWorkRequestBuilder<RandomPhraseWorker>(
            30, TimeUnit.MINUTES // interval
     )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "RandomPhraseWork", // unique name
            ExistingPeriodicWorkPolicy.UPDATE, // replace if already scheduled
            request
        )

    }
}
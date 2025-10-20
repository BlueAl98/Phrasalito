package com.nayibit.phrasalito_presentation.workers.helpers

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.nayibit.phrasalito_presentation.workers.RandomPhraseWorker
import java.util.concurrent.TimeUnit

object RandomPhraseWorkerScheduler {
    private const val WORK_NAME = "random_phrase_work"

    fun start(context: Context) {
        val workManager = WorkManager.getInstance(context)

        // Check if work is already enqueued
        val workInfos = workManager.getWorkInfosForUniqueWork(WORK_NAME).get()
        if (workInfos.any { !it.state.isFinished }) {
            Log.d("RandomPhraseWorkerScheduler", "Work already scheduled, skipping...")
            return
        }

        val periodicWorkRequest = PeriodicWorkRequestBuilder<RandomPhraseWorker>(1, TimeUnit.HOURS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                    .build()
            )
            .build()

        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP, // Keep existing work
            periodicWorkRequest
        )

        Log.d("RandomPhraseWorkerScheduler", "Work scheduled successfully")
    }
}
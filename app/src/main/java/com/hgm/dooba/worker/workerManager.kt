package com.hgm.dooba.worker

import androidx.work.*
import java.util.concurrent.TimeUnit

object CheckUsageWorkerManager {
    // Constrainsts for Worker
    fun createConstraints() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)  // if connected to Internet
        .build()

    // Worker Request
    fun createWorkRequest() = PeriodicWorkRequestBuilder<AutoMailWorker>(15, TimeUnit.MINUTES)  // setting period to 15 Minutes
        .setConstraints(createConstraints())
        // setting a backoff on case the work needs to retry
        .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
        .build()

    // Enqueue Worker
    fun startWorker() {
        WorkManager.getInstance().enqueueUniquePeriodicWork("Check Usage", ExistingPeriodicWorkPolicy.KEEP, createWorkRequest())
    }
}

object SendEmailWorkerManager {
    // Constrainsts for Worker
    fun createConstraints() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)  // if connected to Internet
        .build()

    // Worker Request
    fun createWorkRequest() = PeriodicWorkRequestBuilder<AutoMailWorker>(7, TimeUnit.DAYS)  // setting period to 7 Days
        .setConstraints(createConstraints())
        // setting a backoff on case the work needs to retry
        .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
        .build()

    // Enqueue Worker
    fun startWorker() {
        WorkManager.getInstance().enqueueUniquePeriodicWork("Send Mail", ExistingPeriodicWorkPolicy.KEEP, createWorkRequest())
    }
}
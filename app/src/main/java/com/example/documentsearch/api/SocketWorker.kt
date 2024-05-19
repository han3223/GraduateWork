package com.example.documentsearch.api

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class SocketWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        SocketManager.connect()
        return Result.success()
    }
}
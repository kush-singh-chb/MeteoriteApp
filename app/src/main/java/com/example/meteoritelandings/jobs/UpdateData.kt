package com.example.meteoritelandings.jobs

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.meteoritelandings.Data.loadData
import io.reactivex.functions.Consumer

/**
 * Created by Kush Singh Chibber on 14-12-2020.
 */
class UpdateData(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
    companion object {
        const val TAG = "UpdateMeteoritesJob"
    }

    override fun doWork(): Result {
        loadData(Consumer { }, Consumer { })
        return Result.success()
    }


}

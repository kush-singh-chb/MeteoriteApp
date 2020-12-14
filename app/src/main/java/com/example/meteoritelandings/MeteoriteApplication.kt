package com.example.meteoritelandings

import android.app.Application
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.meteoritelandings.jobs.UpdateJob
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by Kush Singh Chibber on 14-12-2020.
 */
class MeteoriteApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(config)
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(UpdateJob::class.java).build()
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)
    }
}
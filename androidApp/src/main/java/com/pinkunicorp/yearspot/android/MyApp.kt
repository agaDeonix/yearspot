package com.pinkunicorp.yearspot.android

import android.app.Application
import androidx.glance.appwidget.updateAll
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.pinkunicorp.yearspot.android.widget.MyAppWidget
import com.pinkunicorp.yearspot.android.worker.WidgetUpdateWorker
import java.util.concurrent.TimeUnit

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        val widgetUpdateRequest = PeriodicWorkRequestBuilder<WidgetUpdateWorker>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        ).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "widgetUpdateWork",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            widgetUpdateRequest
        )
    }
}
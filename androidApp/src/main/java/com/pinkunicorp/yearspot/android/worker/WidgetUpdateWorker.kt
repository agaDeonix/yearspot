package com.pinkunicorp.yearspot.android.worker

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.pinkunicorp.yearspot.android.widget.MyAppWidget
import kotlinx.coroutines.delay
import java.util.Calendar

class WidgetUpdateWorker(
    private val context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        repeat(60) {
            Log.e("WIDGET", "update")
            val currentTime = Calendar.getInstance()
            val seconds = currentTime.get(Calendar.SECOND)
            GlanceAppWidgetManager(context).getGlanceIds(MyAppWidget::class.java).forEach {
                Log.e("WIDGET", "update widget:$it")

                updateAppWidgetState(context, it) {
                    it[longPreferencesKey("currentTime")] = currentTime.timeInMillis
                }
                MyAppWidget().update(context, it)
            }
//            delay(60_000L - seconds)
            delay(1000)
        }
        return Result.success()
    }

}
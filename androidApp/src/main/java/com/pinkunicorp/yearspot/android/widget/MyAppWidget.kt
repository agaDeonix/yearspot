package com.pinkunicorp.yearspot.android.widget

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.pinkunicorp.yearspot.android.MY_BIRTH_TIME
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale

class MyAppWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            WidgetContent(id)
        }
    }
}

@Composable
fun WidgetContent(id: GlanceId) {
    val currentTime =
        currentState(longPreferencesKey("currentTime")) ?: Calendar.getInstance().timeInMillis
    val lastUpdateTime = Calendar.getInstance().apply {
        timeInMillis = currentTime
    }.time
    val formatter = SimpleDateFormat("HH:mm:ss dd.MM.yyyy", Locale.getDefault())
    val date = formatter.format(lastUpdateTime)

    val currentYear = currentTime - MY_BIRTH_TIME
    Log.e("WIDGET", "widget: $id updated")
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(color = Color(0xFFE0E0E0))
            .cornerRadius(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = currentYear.toYDHM(),
            style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold),
            modifier = GlanceModifier.padding(12.dp)
        )
        Row {
            Text(
                text = date,
                style = TextStyle(fontSize = 12.sp),
                modifier = GlanceModifier.padding(horizontal = 12.dp)
            )
        }
//        Text(
//            text = stringResource(id = R.string.widget_last_update),
//            style = TextStyle(fontSize = 12.sp),
//            modifier = GlanceModifier.padding(12.dp))
//        Text(
//            text = date,
//            style = TextStyle(fontSize = 12.sp),
//            modifier = GlanceModifier.padding(12.dp))
//        Row {
//            Text(
//                text = stringResource(id = R.string.widget_last_update),
//                style = TextStyle(fontSize = 12.sp),
//                modifier = GlanceModifier.padding(12.dp))
//            Text(
//                text = date,
//                style = TextStyle(fontSize = 12.sp),
//                modifier = GlanceModifier.padding(12.dp))
//        }
    }
}

//fun time in millis to years days hours minutes
fun Long.toYDHM(): String {
    val calendar: Calendar = GregorianCalendar(1970, Calendar.JANUARY, 1)
    calendar.timeInMillis = this

    // Получаем конечную дату
    val years = calendar[Calendar.YEAR] - 1970
    val months = calendar[Calendar.MONTH]
    val days = calendar[Calendar.DAY_OF_MONTH] - 1 // потому что начальный день - 1

    val hours = calendar[Calendar.HOUR_OF_DAY]
    val minutes = calendar[Calendar.MINUTE]
    val seconds = calendar[Calendar.SECOND]

    return "${years}y ${months}M ${days}d ${hours}h ${minutes}m ${seconds}s"
}
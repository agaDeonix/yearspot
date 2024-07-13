package com.pinkunicorp.yearspot.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.updateAll
import androidx.lifecycle.lifecycleScope
import com.pinkunicorp.yearspot.android.receiver.MyAppWidgetReceiver
import com.pinkunicorp.yearspot.android.widget.MyAppWidget
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

const val MY_BIRTH_TIME = 537930000000

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            MyAppWidget().updateAll(this@MainActivity)
            GlanceAppWidgetManager(this@MainActivity).apply {
                getGlanceIds(MyAppWidget::class.java).ifEmpty {
                    requestPinGlanceAppWidget(
                        receiver = MyAppWidgetReceiver::class.java
                    )
                }
            }
        }
        val birthDate = Calendar.getInstance().apply {
            timeInMillis = MY_BIRTH_TIME
        }.time
        val formatter = SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault())
        val date = formatter.format(birthDate)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyBirthDayView(day = date)
                }
            }
        }
    }
}

@Composable
fun MyBirthDayView(day: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = day)
    }
}

@Preview
@Composable
fun DefaultPreview() {
    val birthDate = Calendar.getInstance().apply {
        timeInMillis = MY_BIRTH_TIME
    }.time.toString()
    MyApplicationTheme {
        MyBirthDayView(birthDate)
    }
}

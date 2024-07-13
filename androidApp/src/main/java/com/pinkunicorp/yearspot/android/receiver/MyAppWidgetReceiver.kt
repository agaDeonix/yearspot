package com.pinkunicorp.yearspot.android.receiver

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.pinkunicorp.yearspot.android.widget.MyAppWidget

class MyAppWidgetReceiver: GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget
        get() = MyAppWidget()
}
package com.hyejeanmoon.glancedemo

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.*
import androidx.glance.action.*
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.action.actionSendBroadcast
import androidx.glance.appwidget.action.actionStartService
import androidx.glance.appwidget.cornerRadius
import androidx.glance.layout.*
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle

class GlanceWidget : GlanceAppWidget() {

    companion object {
        private val SMALL_BOX = DpSize(90.dp, 90.dp)
        private val BIG_BOX = DpSize(180.dp, 180.dp)
        private val VERY_BIG_BOX = DpSize(300.dp, 300.dp)
        private val ROW = DpSize(180.dp, 48.dp)
        private val LARGE_ROW = DpSize(300.dp, 48.dp)
        private val COLUMN = DpSize(48.dp, 180.dp)
        private val LARGE_COLUMN = DpSize(48.dp, 300.dp)

        private const val ACTION_ACTIVITY = "activity"
        private const val ACTION_SERVICE = "service"
        private const val ACTION_BROADCAST = "broadcast"
        private const val ACTION_CALLBACK = "callback"

        val ACTION_PARAMETERS_KEY = ActionParameters.Key<String>("parameters_keys")
    }

    override val sizeMode = SizeMode.Responsive(
        setOf(SMALL_BOX, BIG_BOX, ROW, LARGE_ROW, COLUMN, LARGE_COLUMN)
    )

    @Composable
    override fun Content() {
        val list = listOf(ACTION_ACTIVITY, ACTION_SERVICE, ACTION_BROADCAST, ACTION_CALLBACK)

        when (LocalSize.current) {
            SMALL_BOX, BIG_BOX, VERY_BIG_BOX -> {
                WidgetBoxView(list)
            }
            ROW, LARGE_ROW -> {
                WidgetRowView(list)
            }
            COLUMN, LARGE_COLUMN -> {
                WidgetColumnView(list)
            }
        }
    }

    @Composable
    fun WidgetRowView(
        list: List<String>
    ) {
        Row(
            modifier = GlanceModifier
                .fillMaxSize()
                .cornerRadius(24.dp)
                .background(Color(0xfff1f1f1)),
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            list.forEach { str ->
                Button(
                    text = str,
                    onClick = getAction(str, LocalContext.current)
                )
            }
        }
    }

    @Composable
    fun WidgetColumnView(
        list: List<String>
    ) {
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .cornerRadius(24.dp)
                .background(Color(0xfff1f1f1)),
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            list.forEach { str ->
                Button(text = str, onClick = getAction(str, LocalContext.current))
            }
        }
    }

    @Composable
    fun WidgetBoxView(
        list: List<String>
    ) {
        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .cornerRadius(24.dp)
                .background(Color(0xfff1f1f1)),
            contentAlignment = Alignment.Center
        ) {
            list.forEachIndexed { i, s ->
                val index = list.size - i
                val boxColors = listOf(Color(0xffF7A998), Color(0xffFA5F3D))
                val color = boxColors[(index - 1) % boxColors.size]
                val boxSize = (LocalSize.current.width * index) / (list.size - 1)
                Text(
                    text = s,
                    modifier = GlanceModifier
                        .size(boxSize)
                        .clickable(getAction(s, LocalContext.current))
                        .background(color),
                    style = TextStyle(textAlign = TextAlign.End)
                )
            }
        }
    }

    private fun getAction(str: String, context: Context): Action {
        return when (str) {
            ACTION_ACTIVITY -> {
                actionStartActivity(
                    MainActivity::class.java,
                    parameters = actionParametersOf(
                        ACTION_PARAMETERS_KEY to str
                    )
                )
            }
            ACTION_BROADCAST -> {
                val intent = Intent(context, GlanceBroadcastReceiver::class.java)
                intent.putExtra(GlanceBroadcastReceiver.BROADCAST_KEY, str)
                actionSendBroadcast(
                    intent
                )
            }
            ACTION_CALLBACK -> {
                actionRunCallback(
                    GlanceCallbackAction::class.java,
                    parameters = actionParametersOf(ACTION_PARAMETERS_KEY to str)
                )
            }
            else -> {
                val intent = Intent(context, GlanceService::class.java)
                intent.putExtra(GlanceService.SERVICE_INTENT_KEY, str)
                actionStartService(intent, isForegroundService = true)
            }
        }
    }

    override suspend fun onDelete(context: Context, glanceId: GlanceId) {
        super.onDelete(context, glanceId)
    }

    override val stateDefinition: GlanceStateDefinition<*>?
        get() = super.stateDefinition

}

class GlanceCallbackAction : ActionCallback {
    override suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        Handler(context.mainLooper).post() {
            val parameter = parameters[GlanceWidget.ACTION_PARAMETERS_KEY]
            Toast.makeText(context, "GlanceClickAction, parameter: $parameter", Toast.LENGTH_LONG)
                .show()
        }
    }
}
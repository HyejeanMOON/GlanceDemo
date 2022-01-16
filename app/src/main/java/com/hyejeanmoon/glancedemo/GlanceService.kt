package com.hyejeanmoon.glancedemo

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast

class GlanceService : Service() {

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val message = intent?.getStringExtra(SERVICE_INTENT_KEY) ?: ""
        Toast.makeText(this, "Glance service is started, $message", Toast.LENGTH_LONG).show()

        stopSelf()

        return START_STICKY
    }

    companion object {
        const val SERVICE_INTENT_KEY = "SERVICE_INTENT_KEY"
    }
}
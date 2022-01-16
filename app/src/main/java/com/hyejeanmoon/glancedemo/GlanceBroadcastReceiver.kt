package com.hyejeanmoon.glancedemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class GlanceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d("GlanceDemo", "GlanceBroadcastReceiver is started!")
        val parameter = p1?.getStringExtra(BROADCAST_KEY) ?: ""
        Toast.makeText(p0, "GlanceBroadcastReceiver is started, parameter: $parameter", Toast.LENGTH_LONG)
            .show()
    }

    companion object {
        const val BROADCAST_KEY = "broadcast_key"
    }
}
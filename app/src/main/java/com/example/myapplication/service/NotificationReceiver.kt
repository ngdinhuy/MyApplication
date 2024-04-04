package com.example.myapplication.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.RemoteInput
import com.example.myapplication.utils.Define

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            val remoteInput = RemoteInput.getResultsFromIntent(it)

            if (remoteInput != null) {
                val title = remoteInput.getCharSequence(Define.KEY_TEXT_REPLY)
                Log.e("NotificatioNReciver", title.toString())
            }
        }

    }

}
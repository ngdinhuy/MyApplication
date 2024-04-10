package com.example.myapplication.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.navigation.NavDeepLinkBuilder
import com.example.myapplication.R
import com.example.myapplication.data.Song
import com.example.myapplication.main.MainActivity
import com.example.myapplication.utils.Define

class NotificationUtils(val context:Context) {
    fun sendNotificationPlaySong(){

    }

    private fun sendNotifcation(song: Song) {
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // tao notification channel
            val name = Define.CHANNEL_ID
            val description = ""
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(Define.CHANNEL_ID, name, importance).apply {
                this.description = description
            }

            notificationManager.createNotificationChannel(channel)
        }

        //tao pending intent khi click vao thong bao
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.main_nav)
            .setDestination(R.id.splashFragment)
            .setComponentName(MainActivity::class.java)
            .createPendingIntent()

        //cai dat button reply notification
        var remoteInput: RemoteInput = RemoteInput.Builder(Define.KEY_TEXT_REPLY).run {
            setLabel("Hint") //hint of edittext
            build()
        }
        val resultIntent = Intent(context, NotificationReceiver::class.java)
        var replyPendingIntent: PendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
        var action = NotificationCompat.Action.Builder(
            R.drawable.baseline_reply_24,
            "Replyyy",//text button
            replyPendingIntent
        ).addRemoteInput(remoteInput)
            .build()

        val remoteView = RemoteViews(context.packageName, R.layout.layout_notification)
        remoteView.setTextViewText(R.id.tv_name, song.name)
        remoteView.setTextViewText(R.id.tv_author, song.author)
//        remoteView.setOnClickResponse()

        // Sử dụng NotificationCompat.Builder để set noi dung cho notification
        var builder = NotificationCompat.Builder(context, Define.CHANNEL_ID)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_small_icon_notification)
            .setContentTitle(song.name)
            .setContentText(song.author)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent) //set su kien click cho thong bao
            .addAction(action)
            .setCustomContentView(remoteView)
        notificationManager.notify(createID(), builder.build())
    }
}
package com.example.myapplication.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.navigation.NavDeepLinkBuilder
import com.example.myapplication.R
import com.example.myapplication.data.Song
import com.example.myapplication.main.MainActivity
import com.example.myapplication.utils.Define
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MediaService() : MediaSessionService() {
    private var mediaSession: MediaSession? = null

    override fun onBind(intent: Intent?): IBinder? {
        super.onBind(intent)
        return null
    }

    override fun onCreate() {
        super.onCreate()
        val player = ExoPlayer.Builder(this).build()
        player.prepare()
        mediaSession = MediaSession.Builder(this, player)
            .build()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? = mediaSession

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        val data = intent?.extras
        val song: Song? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            data?.getSerializable("NEW_SONG", Song::class.java)
        } else {
            data?.getSerializable("NEW_SONG") as? Song
        }
        Log.e("Songggg", song.toString())
        song?.link?.let {
            playMusic(" https://storage.googleapis.com/exoplayer-test-media-0/play.mp3")
        }
        song?.let {
            sendNotifcation(it)
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession?.apply {
            player.release()
            release()
            mediaSession = null
        }
    }

    private fun sendNotifcation(song: Song) {
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
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
        val pendingIntent = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.main_nav)
            .setDestination(R.id.splashFragment)
            .setComponentName(MainActivity::class.java)
            .createPendingIntent()

        //cai dat button reply notification
        var remoteInput: RemoteInput = RemoteInput.Builder(Define.KEY_TEXT_REPLY).run {
            setLabel("Hint") //hint of edittext
            build()
        }
        val resultIntent = Intent(this, NotificationReceiver::class.java)
        var replyPendingIntent: PendingIntent = PendingIntent.getBroadcast(
            this,
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

        val remoteView = RemoteViews(packageName, R.layout.layout_notification)
        remoteView.setTextViewText(R.id.tv_name, song.name)
        remoteView.setTextViewText(R.id.tv_author, song.author)


        // Sử dụng NotificationCompat.Builder để set noi dung cho notification
        var builder = NotificationCompat.Builder(this, Define.CHANNEL_ID)
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

    private fun createID(): Int {
        val now = Date()
        return SimpleDateFormat("ddHHmmss", Locale.US).format(now).toInt()
    }

    private fun playMusic(url: String) {
        mediaSession?.apply {
            val mediaItem = MediaItem.fromUri(url)
            player.setMediaItem(mediaItem)
            player.play()
        }

    }
}
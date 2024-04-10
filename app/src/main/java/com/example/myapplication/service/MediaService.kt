package com.example.myapplication.service

import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.example.myapplication.data.Song
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

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? =
        mediaSession

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        val data = intent?.extras
        val song: Song? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            data?.getSerializable("NEW_SONG", Song::class.java)
        } else {
            data?.getSerializable("NEW_SONG") as? Song
        }
        song?.link?.let {
            playMusic(it)
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





    private fun playMusic(url: String) {
        mediaSession?.apply {
            val mediaItem = MediaItem.fromUri(url)
            player.setMediaItem(mediaItem)
            player.play()
        }

    }
}
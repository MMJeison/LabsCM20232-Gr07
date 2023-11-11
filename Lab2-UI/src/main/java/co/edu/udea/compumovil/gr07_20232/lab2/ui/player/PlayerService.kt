package co.edu.udea.compumovil.gr07_20232.lab2.ui.player

import android.app.Service
import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import co.edu.udea.compumovil.gr07_20232.lab2.R
import java.io.IOException
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


class PlayerService : Service() {

    private val binder: IBinder = LocalBinder()
    private var mediaPlayer: MediaPlayer? = null
    private var duration: String? = null
    private var timer: ScheduledExecutorService? = null
    private lateinit var sp: SharedPreferences
    private lateinit var mUri: Uri
    var songUrl: String = "https://nyt.simplecastaudio.com/bbbcc290-ed3b-44a2-8e5d-5513e38cfe20/episodes/e7db6450-5024-40a4-b537-63fdd37b5915/audio/128/default.mp3/default.mp3_ywr3ahjkcgo_7c00e8f18999a315bf15cce31056ba17_55462015.mp3?awCollectionId=bbbcc290-ed3b-44a2-8e5d-5513e38cfe20&amp;awEpisodeId=e7db6450-5024-40a4-b537-63fdd37b5915&hash_redirect=1&x-total-bytes=55462015&x-ais-classified=streaming&listeningSessionID=0CD_382_307__3848c19da800b0280958142d9e5d73586c76b570"
    private lateinit var filename: String

    inner class LocalBinder : Binder() {
        fun getService(): PlayerService {
            return this@PlayerService
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        songUrl = intent.data.toString()
        createMediaPlayer(songUrl)
        sp = getSharedPreferences("sp", MODE_PRIVATE)
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    private fun createMediaPlayer(url: String) {
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
        try {
            mediaPlayer?.setDataSource(songUrl)
            mediaPlayer?.prepare()
            mediaPlayer?.setOnCompletionListener {
                releaseMediaPlayer()
                stopSelf()
            }
            //filename = getNameFromUri(uri)
            val millis = mediaPlayer?.duration ?: 0
            val totalSecs = TimeUnit.SECONDS.convert(millis.toLong(), TimeUnit.MILLISECONDS)
            val mins = TimeUnit.MINUTES.convert(totalSecs, TimeUnit.SECONDS)
            val secs = totalSecs - (mins * 60)
            duration = "$mins:$secs"
        } catch (e: IOException) {
            // Handle IOException
        }
    }

    fun updateMediaData() {
        // Update UI elements with media data
    }

    /*private fun getNameFromUri(uri: Uri): String {
        var fileName = ""
        var cursor: Cursor? = null
        try {
            cursor = contentResolver.query(uri, arrayOf(MediaStore.Images.ImageColumns.DISPLAY_NAME), null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                fileName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME))
            }
        } finally {
            cursor?.close()
        }
        return fileName
    }*/

    private fun releaseMediaPlayer() {
        timer?.shutdown()
        mediaPlayer?.release()
        mediaPlayer = null
        sp.edit().putString("created", "false").commit()
        // Update UI elements after release
    }

    fun playOrPause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                // Update UI elements for pause
                timer?.shutdown()
            } else {
                it.start()
                // Update UI elements for play

                timer = Executors.newScheduledThreadPool(1)
                /*timer?.scheduleAtFixedRate({
                    if (mediaPlayer != null && !MainActivity.seekbar1.isPressed) {
                        MainActivity.seekbar1.progress = mediaPlayer!!.currentPosition
                    }
                }, 10, 10, TimeUnit.MILLISECONDS) TODO? */
            }
        }
    }

    fun updatePlayingTime() {
        mediaPlayer?.let {
            val millis = it.currentPosition
            val totalSecs = TimeUnit.SECONDS.convert(millis.toLong(), TimeUnit.MILLISECONDS)
            val mins = TimeUnit.MINUTES.convert(totalSecs, TimeUnit.SECONDS)
            val secs = totalSecs - (mins * 60)
            // Update UI elements with playing time
        }
    }

    /*fun seekMediaPlayer() {
        mediaPlayer?.seekTo(MainActivity.seekbar1.progress)
    }*/
}
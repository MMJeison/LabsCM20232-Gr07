package co.edu.udea.compumovil.gr07_20232.lab2.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import co.edu.udea.compumovil.gr07_20232.lab2.ui.player.PlayerService
import co.edu.udea.compumovil.gr07_20232.lab2.ui.theme.JetcasterTheme
import com.google.accompanist.adaptive.calculateDisplayFeatures

class MainActivity : ComponentActivity() {

    private var mBound = false
    private var mService: PlayerService? = null
    private val sp: SharedPreferences by lazy { getSharedPreferences("sp", MODE_PRIVATE) }


    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as PlayerService.LocalBinder
            mService = binder.getService()
            mService?.updateMediaData()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //sp = getSharedPreferences("sp", MODE_PRIVATE)


        val intent = Intent(this@MainActivity, PlayerService::class.java)
        intent.data = Uri.parse("https://nyt.simplecastaudio.com/bbbcc290-ed3b-44a2-8e5d-5513e38cfe20/episodes/e7db6450-5024-40a4-b537-63fdd37b5915/audio/128/default.mp3/default.mp3_ywr3ahjkcgo_7c00e8f18999a315bf15cce31056ba17_55462015.mp3?awCollectionId=bbbcc290-ed3b-44a2-8e5d-5513e38cfe20&amp;awEpisodeId=e7db6450-5024-40a4-b537-63fdd37b5915&hash_redirect=1&x-total-bytes=55462015&x-ais-classified=streaming&listeningSessionID=0CD_382_307__3848c19da800b0280958142d9e5d73586c76b570")
        startService(intent)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
        sp.edit().putString("created", "true").apply()

        fun onStop() {
            super.onStop()
            if (mService != null) {
                if (mBound) {
                    unbindService(connection)
                    mBound = false
                }
            }
        }

        fun onStart() {
            super.onStart()
            if (!mBound) {
                if (sp.getString("created", "") == "true") {
                    val intent = Intent(this@MainActivity, PlayerService::class.java)
                    bindService(intent, connection, Context.BIND_AUTO_CREATE)
                }
            }
        }

        // This app draws behind the system bars, so we want to handle fitting system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            val displayFeatures = calculateDisplayFeatures(this)
            val onPlayPauseClick: () -> Unit = {
                if (mBound) {
                    mService?.playOrPause()
                }
            }



            JetcasterTheme {
                JetcasterApp(
                    windowSizeClass,
                    displayFeatures,
                    onPlayPauseClick = onPlayPauseClick
                )
            }
        }
    }
}

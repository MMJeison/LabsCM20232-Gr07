package co.edu.udea.compumovil.gr07_20232.lab2.ui.player

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forward30
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.rounded.PauseCircleFilled
import androidx.compose.material.icons.rounded.PlayCircleFilled
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import co.edu.udea.compumovil.gr07_20232.lab2.R
import co.edu.udea.compumovil.gr07_20232.lab2.ui.Song

@Composable
private fun PlayButton(
    modifier: Modifier = Modifier,
    playerButtonSize: Dp = 72.dp,
    mplayer: MediaPlayer,
    uiState: PlayerUiState,
    onPlayPauseClick: () -> Unit
) {
    var isp by remember { mutableStateOf( if(Song.current.equals(uiState.uri)) mplayer.isPlaying else false ) }
    val icon = if (isp) Icons.Rounded.PauseCircleFilled else Icons.Rounded.PlayCircleFilled
    val context = LocalContext.current


    fun handlePlayPause () {
        if (Song.current.equals(uiState.uri)) {
            if (mplayer.isPlaying) {
                mplayer.pause()
                isp = false
                Intent(context.applicationContext, MediaPlayerService::class.java).also {
                    it.action = MediaPlayerService.Actions.STOP.toString()
                    context.startService(it)
                }
            } else {
                mplayer.start()
                isp = true
                Intent(context.applicationContext, MediaPlayerService::class.java).also {
                    it.action = MediaPlayerService.Actions.START.toString()
                    context.startService(it)
                }
            }
        }else {
            Song.current = uiState.uri
            Song.refresh()
            Song.refresh = fun(){
                isp = false
            }
            mplayer.reset()
            mplayer.setDataSource(Song.songUrl)
            mplayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mplayer.prepareAsync()
            mplayer.setOnPreparedListener {
                    mediaPlayer -> run {
                mediaPlayer.start()
                isp = true
            }
            }
        }
    }


    Image(
        imageVector = icon,
        contentDescription = stringResource(R.string.cd_play),
        contentScale = ContentScale.Fit,
        colorFilter = ColorFilter.tint(LocalContentColor.current),
        modifier = modifier.size(playerButtonSize)
            .semantics { role = Role.Button }
            .then(
                Modifier.clickable {
                    onPlayPauseClick().also { handlePlayPause() }
                }
            )
    )
}

@Composable
public fun PlayerButtons(
    modifier: Modifier = Modifier,
    playerButtonSize: Dp = 72.dp,
    sideButtonSize: Dp = 48.dp,
    mplayer: MediaPlayer,
    uiState: PlayerUiState,
    onPlayPauseClick: () -> Unit
) {


    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val buttonsModifier = Modifier
            .size(sideButtonSize)
            .semantics { role = Role.Button }

        Image(
            imageVector = Icons.Filled.SkipPrevious,
            contentDescription = stringResource(R.string.cd_skip_previous),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            modifier = buttonsModifier
        )
        Image(
            imageVector = Icons.Filled.Replay10,
            contentDescription = stringResource(R.string.cd_reply10),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            modifier = buttonsModifier
        )
        PlayButton(mplayer = mplayer, uiState = uiState, onPlayPauseClick = onPlayPauseClick)
        Image(
            imageVector = Icons.Filled.Forward30,
            contentDescription = stringResource(R.string.cd_forward30),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            modifier = buttonsModifier
        )
        Image(
            imageVector = Icons.Filled.SkipNext,
            contentDescription = stringResource(R.string.cd_skip_next),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            modifier = buttonsModifier
        )
    }
}
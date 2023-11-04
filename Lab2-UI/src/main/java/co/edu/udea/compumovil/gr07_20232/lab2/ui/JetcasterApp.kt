/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.edu.udea.compumovil.gr07_20232.lab2.ui

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.window.layout.DisplayFeature
import co.edu.udea.compumovil.gr07_20232.lab2.R
import co.edu.udea.compumovil.gr07_20232.lab2.ui.home.Home
import co.edu.udea.compumovil.gr07_20232.lab2.ui.player.PlayerScreen
import co.edu.udea.compumovil.gr07_20232.lab2.ui.player.PlayerViewModel

@Composable
fun JetcasterApp(
    windowSizeClass: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    appState: JetcasterAppState = rememberJetcasterAppState()
) {
    val MLPlayer = MediaPlayer()
    val url = "https://nyt.simplecastaudio.com/bbbcc290-ed3b-44a2-8e5d-5513e38cfe20/episodes/e7db6450-5024-40a4-b537-63fdd37b5915/audio/128/default.mp3/default.mp3_ywr3ahjkcgo_7c00e8f18999a315bf15cce31056ba17_55462015.mp3?awCollectionId=bbbcc290-ed3b-44a2-8e5d-5513e38cfe20&amp;awEpisodeId=e7db6450-5024-40a4-b537-63fdd37b5915&hash_redirect=1&x-total-bytes=55462015&x-ais-classified=streaming&listeningSessionID=0CD_382_307__3848c19da800b0280958142d9e5d73586c76b570"
    MLPlayer.setDataSource(url)
    MLPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
    MLPlayer.prepareAsync()
    if (appState.isOnline) {
        NavHost(
            navController = appState.navController,
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route) { backStackEntry ->
                Home(
                    navigateToPlayer = { episodeUri ->
                        appState.navigateToPlayer(episodeUri, backStackEntry)
                    },
                    mplayer = MLPlayer
                )
            }
            composable(Screen.Player.route) { backStackEntry ->
                val playerViewModel: PlayerViewModel = viewModel(
                    factory = PlayerViewModel.provideFactory(
                        owner = backStackEntry,
                        defaultArgs = backStackEntry.arguments
                    )
                )
                PlayerScreen(
                    playerViewModel,
                    windowSizeClass,
                    displayFeatures,
                    onBackPress = appState::navigateBack,
                    mplayer = MLPlayer
                )
            }
        }
    } else {
        OfflineDialog { appState.refreshOnline() }
    }
}

@Composable
fun OfflineDialog(onRetry: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = stringResource(R.string.connection_error_title)) },
        text = { Text(text = stringResource(R.string.connection_error_message)) },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text(stringResource(R.string.retry_label))
            }
        }
    )
}

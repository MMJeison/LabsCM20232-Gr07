/*
 * Copyright 2021 The Android Open Source Project
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

package co.edu.udea.compumovil.gr07_20232.lab2.ui.player

import android.media.AudioManager
import android.media.MediaPlayer
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Forward30
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.rounded.PlayCircleFilled
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import coil.compose.AsyncImage
import coil.request.ImageRequest
import co.edu.udea.compumovil.gr07_20232.lab2.R
import co.edu.udea.compumovil.gr07_20232.lab2.ui.theme.JetcasterTheme
import co.edu.udea.compumovil.gr07_20232.lab2.ui.theme.MinContrastOfPrimaryVsSurface
import co.edu.udea.compumovil.gr07_20232.lab2.util.DynamicThemePrimaryColorsFromImage
import co.edu.udea.compumovil.gr07_20232.lab2.util.contrastAgainst
import co.edu.udea.compumovil.gr07_20232.lab2.util.isBookPosture
import co.edu.udea.compumovil.gr07_20232.lab2.util.isSeparatingPosture
import co.edu.udea.compumovil.gr07_20232.lab2.util.isTableTopPosture
import co.edu.udea.compumovil.gr07_20232.lab2.util.rememberDominantColorState
import co.edu.udea.compumovil.gr07_20232.lab2.util.verticalGradientScrim
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.google.accompanist.adaptive.VerticalTwoPaneStrategy
import java.time.Duration

/**
 * Stateful version of the Podcast player
 */
@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel,
    windowSizeClass: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    onBackPress: () -> Unit,
    mplayer: MediaPlayer,
    onPlayPauseClick: () -> Unit
) {
    val uiState = viewModel.uiState
    PlayerScreen(uiState, windowSizeClass, displayFeatures, onBackPress, mplayer = mplayer, onPlayPauseClick = onPlayPauseClick)
}

/**
 * Stateless version of the Player screen
 */
@Composable
private fun PlayerScreen(
    uiState: PlayerUiState,
    windowSizeClass: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier,
    mplayer: MediaPlayer,
    onPlayPauseClick: () -> Unit
) {

    Surface(modifier) {
        if (uiState.podcastName.isNotEmpty()) {
            PlayerContent(uiState, windowSizeClass, displayFeatures, onBackPress, mplayer = mplayer, onPlayPauseClick = onPlayPauseClick)
        } else {
            FullScreenLoading()
        }
    }
}

@Composable
fun PlayerContent(
    uiState: PlayerUiState,
    windowSizeClass: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier,
    mplayer: MediaPlayer,
    onPlayPauseClick: () -> Unit
) {

    // val context = LocalContext.current
    // Toast.makeText(context, uiState.uri, Toast.LENGTH_LONG).show()

    PlayerDynamicTheme(uiState.podcastImageUrl) {
        val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()

        // Use a two pane layout if there is a fold impacting layout (meaning it is separating
        // or non-flat) or if we have a large enough width to show both.
        if (
            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded ||
            isBookPosture(foldingFeature) ||
            isTableTopPosture(foldingFeature) ||
            isSeparatingPosture(foldingFeature)
        ) {
            // Determine if we are going to be using a vertical strategy (as if laying out
            // both sides in a column). We want to do so if we are in a tabletop posture,
            // or we have an impactful horizontal fold. Otherwise, we'll use a horizontal strategy.
            val usingVerticalStrategy =
                isTableTopPosture(foldingFeature) ||
                    (
                        isSeparatingPosture(foldingFeature) &&
                            foldingFeature.orientation == FoldingFeature.Orientation.HORIZONTAL
                        )

            if (usingVerticalStrategy) {
                TwoPane(
                    first = {
                        PlayerContentTableTopTop(uiState = uiState)
                    },
                    second = {
                        PlayerContentTableTopBottom(
                            uiState = uiState,
                            onBackPress = onBackPress,
                            mplayer = mplayer,
                            onPlayPauseClick = onPlayPauseClick
                        )
                    },
                    strategy = VerticalTwoPaneStrategy(splitFraction = 0.5f),
                    displayFeatures = displayFeatures,
                    modifier = modifier,
                )
            } else {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .verticalGradientScrim(
                            color = MaterialTheme.colors.primary.copy(alpha = 0.50f),
                            startYPercentage = 1f,
                            endYPercentage = 0f
                        )
                        .systemBarsPadding()
                        .padding(horizontal = 8.dp)
                ) {
                    TopAppBar(onBackPress = onBackPress)
                    TwoPane(
                        first = {
                            PlayerContentBookStart(uiState = uiState)
                        },
                        second = {
                            PlayerContentBookEnd(uiState = uiState, mplayer = mplayer, onPlayPauseClick = onPlayPauseClick)
                        },
                        strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f),
                        displayFeatures = displayFeatures
                    )
                }
            }
        } else {
            PlayerContentRegular(uiState, onBackPress, modifier, mplayer = mplayer, onPlayPauseClick = onPlayPauseClick)
        }
    }
}

/**
 * The UI for the top pane of a tabletop layout.
 */
@Composable
private fun PlayerContentRegular(
    uiState: PlayerUiState,
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier,
    mplayer: MediaPlayer,
    onPlayPauseClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalGradientScrim(
                color = MaterialTheme.colors.primary.copy(alpha = 0.50f),
                startYPercentage = 1f,
                endYPercentage = 0f
            )
            .systemBarsPadding()
            .padding(horizontal = 8.dp)
    ) {
        TopAppBar(onBackPress = onBackPress)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            PlayerImage(
                podcastImageUrl = uiState.podcastImageUrl,
                modifier = Modifier.weight(10f)
            )
            Spacer(modifier = Modifier.height(32.dp))
            PodcastDescription(uiState.title, uiState.podcastName)
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(10f)
            ) {
                PlayerSlider(uiState.duration)
                PlayerButtons(Modifier.padding(vertical = 8.dp), mplayer = mplayer, uiState = uiState, onPlayPauseClick = onPlayPauseClick)
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

/**
 * The UI for the top pane of a tabletop layout.
 */
@Composable
private fun PlayerContentTableTopTop(
    uiState: PlayerUiState,
    modifier: Modifier = Modifier
) {
    // Content for the top part of the screen
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalGradientScrim(
                color = MaterialTheme.colors.primary.copy(alpha = 0.50f),
                startYPercentage = 1f,
                endYPercentage = 0f
            )
            .windowInsetsPadding(
                WindowInsets.systemBars.only(
                    WindowInsetsSides.Horizontal + WindowInsetsSides.Top
                )
            )
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlayerImage(uiState.podcastImageUrl)
    }
}

/**
 * The UI for the bottom pane of a tabletop layout.
 */
@Composable
private fun PlayerContentTableTopBottom(
    uiState: PlayerUiState,
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier,
    mplayer: MediaPlayer,
    onPlayPauseClick: () -> Unit
) {
    // Content for the table part of the screen
    Column(
        modifier = modifier
            .windowInsetsPadding(
                WindowInsets.systemBars.only(
                    WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                )
            )
            .padding(horizontal = 32.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(onBackPress = onBackPress)
        PodcastDescription(
            title = uiState.title,
            podcastName = uiState.podcastName,
            titleTextStyle = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.weight(0.5f))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(10f)
        ) {
            PlayerButtons(
                playerButtonSize = 92.dp,
                modifier = Modifier.padding(top = 8.dp),
                mplayer = mplayer,
                uiState = uiState,
                onPlayPauseClick = onPlayPauseClick
            )
            PlayerSlider(uiState.duration)
        }
    }
}

/**
 * The UI for the start pane of a book layout.
 */
@Composable
private fun PlayerContentBookStart(
    uiState: PlayerUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        PodcastInformation(
            uiState.title,
            uiState.podcastName,
            uiState.summary
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}

/**
 * The UI for the end pane of a book layout.
 */
@Composable
private fun PlayerContentBookEnd(
    uiState: PlayerUiState,
    modifier: Modifier = Modifier,
    mplayer: MediaPlayer,
    onPlayPauseClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        PlayerImage(
            podcastImageUrl = uiState.podcastImageUrl,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .weight(1f)
        )
        PlayerSlider(uiState.duration)
        PlayerButtons(Modifier.padding(vertical = 8.dp), mplayer = mplayer, uiState = uiState, onPlayPauseClick = onPlayPauseClick)
    }
}

@Composable
private fun TopAppBar(onBackPress: () -> Unit) {
    Row(Modifier.fillMaxWidth()) {
        IconButton(onClick = onBackPress) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.cd_back)
            )
        }
        Spacer(Modifier.weight(1f))
        IconButton(onClick = { /* TODO */ }) {
            Icon(
                imageVector = Icons.Default.PlaylistAdd,
                contentDescription = stringResource(R.string.cd_add)
            )
        }
        IconButton(onClick = { /* TODO */ }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(R.string.cd_more)
            )
        }
    }
}

@Composable
private fun PlayerImage(
    podcastImageUrl: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(podcastImageUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .sizeIn(maxWidth = 500.dp, maxHeight = 500.dp)
            .aspectRatio(1f)
            .clip(MaterialTheme.shapes.medium)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PodcastDescription(
    title: String,
    podcastName: String,
    titleTextStyle: TextStyle = MaterialTheme.typography.h5
) {
    Text(
        text = title,
        style = titleTextStyle,
        maxLines = 1,
        modifier = Modifier.basicMarquee()
    )
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = podcastName,
            style = MaterialTheme.typography.body2,
            maxLines = 1
        )
    }
}

@Composable
private fun PodcastInformation(
    title: String,
    name: String,
    summary: String,
    titleTextStyle: TextStyle = MaterialTheme.typography.h5,
    nameTextStyle: TextStyle = MaterialTheme.typography.h3,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Text(
            text = name,
            style = nameTextStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = title,
            style = titleTextStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(32.dp))
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = summary,
                style = MaterialTheme.typography.body2,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun PlayerSlider(episodeDuration: Duration?) {
    if (episodeDuration != null) {
        Column(Modifier.fillMaxWidth()) {
            Slider(value = 0f, onValueChange = { })
            Row(Modifier.fillMaxWidth()) {
                Text(text = "0s")
                Spacer(modifier = Modifier.weight(1f))
                Text("${episodeDuration.seconds}s")
            }
        }
    }
}


/**
 * Theme that updates the colors dynamically depending on the podcast image URL
 */
@Composable
private fun PlayerDynamicTheme(
    podcastImageUrl: String,
    content: @Composable () -> Unit
) {
    val surfaceColor = MaterialTheme.colors.surface
    val dominantColorState = rememberDominantColorState(
        defaultColor = MaterialTheme.colors.surface
    ) { color ->
        // We want a color which has sufficient contrast against the surface color
        color.contrastAgainst(surfaceColor) >= MinContrastOfPrimaryVsSurface
    }
    DynamicThemePrimaryColorsFromImage(dominantColorState) {
        // Update the dominantColorState with colors coming from the podcast image URL
        LaunchedEffect(podcastImageUrl) {
            if (podcastImageUrl.isNotEmpty()) {
                dominantColorState.updateColorsFromImageUrl(podcastImageUrl)
            } else {
                dominantColorState.reset()
            }
        }
        content()
    }
}

/**
 * Full screen circular progress indicator
 */
@Composable
private fun FullScreenLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun TopAppBarPreview() {
    JetcasterTheme {
        TopAppBar(onBackPress = { })
    }
}
/*
@Preview
@Composable
fun PlayerButtonsPreview() {
    val MLPlayer = MediaPlayer()
    val url = "https://nyt.simplecastaudio.com/bbbcc290-ed3b-44a2-8e5d-5513e38cfe20/episodes/e7db6450-5024-40a4-b537-63fdd37b5915/audio/128/default.mp3/default.mp3_ywr3ahjkcgo_7c00e8f18999a315bf15cce31056ba17_55462015.mp3?awCollectionId=bbbcc290-ed3b-44a2-8e5d-5513e38cfe20&amp;awEpisodeId=e7db6450-5024-40a4-b537-63fdd37b5915&hash_redirect=1&x-total-bytes=55462015&x-ais-classified=streaming&listeningSessionID=0CD_382_307__3848c19da800b0280958142d9e5d73586c76b570"
    MLPlayer.setDataSource(url)
    MLPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
    MLPlayer.prepareAsync()
    JetcasterTheme {
        PlayerButtons(mplayer = MLPlayer)
    }
}
*/

/*
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(device = Devices.PHONE)
@Preview(device = Devices.FOLDABLE)
@Preview(device = Devices.TABLET)
@Preview(device = Devices.DESKTOP)
@Composable
fun PlayerScreenPreview() {
    val MLPlayer = MediaPlayer()
    val url = "https://nyt.simplecastaudio.com/bbbcc290-ed3b-44a2-8e5d-5513e38cfe20/episodes/e7db6450-5024-40a4-b537-63fdd37b5915/audio/128/default.mp3/default.mp3_ywr3ahjkcgo_7c00e8f18999a315bf15cce31056ba17_55462015.mp3?awCollectionId=bbbcc290-ed3b-44a2-8e5d-5513e38cfe20&amp;awEpisodeId=e7db6450-5024-40a4-b537-63fdd37b5915&hash_redirect=1&x-total-bytes=55462015&x-ais-classified=streaming&listeningSessionID=0CD_382_307__3848c19da800b0280958142d9e5d73586c76b570"
    MLPlayer.setDataSource(url)
    MLPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
    MLPlayer.prepareAsync()

    JetcasterTheme {
        BoxWithConstraints {
            PlayerScreen(
                PlayerUiState(
                    title = "Title",
                    duration = Duration.ofHours(2),
                    podcastName = "Podcast"
                ),
                displayFeatures = emptyList(),
                windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(maxWidth, maxHeight)),
                onBackPress = { },
                mplayer = MLPlayer
            )
        }
    }
}*/

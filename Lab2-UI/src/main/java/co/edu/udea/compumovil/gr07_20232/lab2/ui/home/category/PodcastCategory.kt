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

package co.edu.udea.compumovil.gr07_20232.lab2.ui.home.category

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.rounded.PauseCircleFilled
import androidx.compose.material.icons.rounded.PlayCircleFilled
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import androidx.constraintlayout.compose.Dimension.Companion.preferredWrapContent
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import co.edu.udea.compumovil.gr07_20232.lab2.R
import co.edu.udea.compumovil.gr07_20232.lab2.data.Episode
import co.edu.udea.compumovil.gr07_20232.lab2.data.EpisodeToPodcast
import co.edu.udea.compumovil.gr07_20232.lab2.data.Podcast
import co.edu.udea.compumovil.gr07_20232.lab2.data.PodcastWithExtraInfo
import co.edu.udea.compumovil.gr07_20232.lab2.ui.Song
import co.edu.udea.compumovil.gr07_20232.lab2.ui.home.PreviewEpisodes
import co.edu.udea.compumovil.gr07_20232.lab2.ui.home.PreviewPodcasts
import co.edu.udea.compumovil.gr07_20232.lab2.ui.theme.JetcasterTheme
import co.edu.udea.compumovil.gr07_20232.lab2.ui.theme.Keyline1
import co.edu.udea.compumovil.gr07_20232.lab2.util.ToggleFollowPodcastIconButton
import co.edu.udea.compumovil.gr07_20232.lab2.util.viewModelProviderFactoryOf
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun PodcastCategory(
    categoryId: Long,
    navigateToPlayer: (String) -> Unit,
    modifier: Modifier = Modifier,
    mplayer: MediaPlayer
) {
    /**
     * CategoryEpisodeListViewModel requires the category as part of it's constructor, therefore
     * we need to assist with it's instantiation with a custom factory and custom key.
     */
    val viewModel: PodcastCategoryViewModel = viewModel(
        // We use a custom key, using the category parameter
        key = "category_list_$categoryId",
        factory = viewModelProviderFactoryOf { PodcastCategoryViewModel(categoryId) }
    )

    val viewState by viewModel.state.collectAsStateWithLifecycle()

    /**
     * TODO: reset scroll position when category changes
     */
    Column(modifier = modifier) {
        CategoryPodcasts(viewState.topPodcasts, viewModel)
        EpisodeList(viewState.episodes, navigateToPlayer, mplayer)
    }
}

@Composable
private fun CategoryPodcasts(
    topPodcasts: List<PodcastWithExtraInfo>,
    viewModel: PodcastCategoryViewModel
) {
    CategoryPodcastRow(
        podcasts = topPodcasts,
        onTogglePodcastFollowed = viewModel::onTogglePodcastFollowed,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun EpisodeList(
    episodes: List<EpisodeToPodcast>,
    navigateToPlayer: (String) -> Unit,
    mplayer: MediaPlayer
) {
    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.Center
    ) {

        items(episodes, key = { it.episode.uri }) { item ->
            EpisodeListItem(
                episode = item.episode,
                podcast = item.podcast,
                onClick = navigateToPlayer,
                modifier = Modifier.fillParentMaxWidth(),
                mplayer
            )
        }
    }
}

@Composable
fun EpisodeListItem(
    episode: Episode,
    podcast: Podcast,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    mplayer: MediaPlayer
) {
    var isp by remember { mutableStateOf( if(Song.current.equals(episode.uri)) mplayer.isPlaying else false ) }
    val icon = if (isp) Icons.Rounded.PauseCircleFilled else Icons.Rounded.PlayCircleFilled

    fun handlePlayPause () {
        if(Song.current.equals(episode.uri)) {
            if (mplayer.isPlaying) {
                mplayer.pause()
                isp = false
            } else {
                mplayer.start()
                isp = true
            }
        } else {
            Song.current = episode.uri
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

    ConstraintLayout(modifier = modifier.clickable { onClick(episode.uri) }) {
        val (
            divider, episodeTitle, podcastTitle, image, playIcon,
            date, addPlaylist, overflow
        ) = createRefs()

        Divider(
            Modifier.constrainAs(divider) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)

                width = fillToConstraints
            }
        )

        // If we have an image Url, we can show it using Coil
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(podcast.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(MaterialTheme.shapes.medium)
                .constrainAs(image) {
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(parent.top, 16.dp)
                },
        )

        Text(
            text = episode.title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(episodeTitle) {
                linkTo(
                    start = parent.start,
                    end = image.start,
                    startMargin = Keyline1,
                    endMargin = 16.dp,
                    bias = 0f
                )
                top.linkTo(parent.top, 16.dp)
                height = preferredWrapContent
                width = preferredWrapContent
            }
        )

        val titleImageBarrier = createBottomBarrier(podcastTitle, image)

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = podcast.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.constrainAs(podcastTitle) {
                    linkTo(
                        start = parent.start,
                        end = image.start,
                        startMargin = Keyline1,
                        endMargin = 16.dp,
                        bias = 0f
                    )
                    top.linkTo(episodeTitle.bottom, 6.dp)
                    height = preferredWrapContent
                    width = preferredWrapContent
                }
            )
        }

        // PlayButton(mplayer = mplayer)
        Image(
            imageVector = icon,
            contentDescription = stringResource(R.string.cd_play),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false, radius = 24.dp)
                ) { handlePlayPause() }
                .size(48.dp)
                .padding(6.dp)
                .semantics { role = Role.Button }
                .constrainAs(playIcon) {
                    start.linkTo(parent.start, Keyline1)
                    top.linkTo(titleImageBarrier, margin = 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                }
        )

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = when {
                    episode.duration != null -> {
                        // If we have the duration, we combine the date/duration via a
                        // formatted string
                        stringResource(
                            R.string.episode_date_duration,
                            MediumDateFormatter.format(episode.published),
                            episode.duration.toMinutes().toInt()
                        )
                    }
                    // Otherwise we just use the date
                    else -> MediumDateFormatter.format(episode.published)
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.constrainAs(date) {
                    centerVerticallyTo(playIcon)
                    linkTo(
                        start = playIcon.end,
                        startMargin = 12.dp,
                        end = addPlaylist.start,
                        endMargin = 16.dp,
                        bias = 0f // float this towards the start
                    )
                    width = preferredWrapContent
                }
            )

            IconButton(
                onClick = { /* TODO */ },
                modifier = Modifier.constrainAs(addPlaylist) {
                    end.linkTo(overflow.start)
                    centerVerticallyTo(playIcon)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.PlaylistAdd,
                    contentDescription = stringResource(R.string.cd_add)
                )
            }

            IconButton(
                onClick = { /* TODO */ },
                modifier = Modifier.constrainAs(overflow) {
                    end.linkTo(parent.end, 8.dp)
                    centerVerticallyTo(playIcon)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.cd_more)
                )
            }
        }
    }
}

@Composable
private fun CategoryPodcastRow(
    podcasts: List<PodcastWithExtraInfo>,
    onTogglePodcastFollowed: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val lastIndex = podcasts.size - 1
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(start = Keyline1, top = 8.dp, end = Keyline1, bottom = 24.dp)
    ) {
        itemsIndexed(items = podcasts) { index: Int,
            (podcast, _, isFollowed): PodcastWithExtraInfo ->
            TopPodcastRowItem(
                podcastTitle = podcast.title,
                podcastImageUrl = podcast.imageUrl,
                isFollowed = isFollowed,
                onToggleFollowClicked = { onTogglePodcastFollowed(podcast.uri) },
                modifier = Modifier.width(128.dp)
            )

            if (index < lastIndex) Spacer(Modifier.width(24.dp))
        }
    }
}

@Composable
private fun TopPodcastRowItem(
    podcastTitle: String,
    isFollowed: Boolean,
    modifier: Modifier = Modifier,
    onToggleFollowClicked: () -> Unit,
    podcastImageUrl: String? = null,
) {
    Column(
        modifier.semantics(mergeDescendants = true) {}
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .align(Alignment.CenterHorizontally)
        ) {
            if (podcastImageUrl != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(podcastImageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium),
                )
            }

            ToggleFollowPodcastIconButton(
                onClick = onToggleFollowClicked,
                isFollowed = isFollowed,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }

        Text(
            text = podcastTitle,
            style = MaterialTheme.typography.body2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        )
    }
}

private val MediumDateFormatter by lazy {
    DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
}

@Preview
@Composable
fun PreviewEpisodeListItem() {
    val MLPlayer = MediaPlayer()
    val url = "https://nyt.simplecastaudio.com/bbbcc290-ed3b-44a2-8e5d-5513e38cfe20/episodes/e7db6450-5024-40a4-b537-63fdd37b5915/audio/128/default.mp3/default.mp3_ywr3ahjkcgo_7c00e8f18999a315bf15cce31056ba17_55462015.mp3?awCollectionId=bbbcc290-ed3b-44a2-8e5d-5513e38cfe20&amp;awEpisodeId=e7db6450-5024-40a4-b537-63fdd37b5915&hash_redirect=1&x-total-bytes=55462015&x-ais-classified=streaming&listeningSessionID=0CD_382_307__3848c19da800b0280958142d9e5d73586c76b570"
    MLPlayer.setDataSource(url)
    MLPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
    MLPlayer.prepareAsync()
    JetcasterTheme {
        EpisodeListItem(
            episode = PreviewEpisodes[0],
            podcast = PreviewPodcasts[0],
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            mplayer = MLPlayer
        )
    }
}

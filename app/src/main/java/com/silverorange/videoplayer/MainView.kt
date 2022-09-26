package com.silverorange.videoplayer

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import dev.jeziellago.compose.markdowntext.MarkdownText
import org.json.JSONObject

@Composable
fun VideoScreen(mainViewModel: MainViewModel) {
    var videoList by remember { mainViewModel.videoList }
    var currentVideoIndex by remember { mainViewModel.currentVideoIndex }
    var videoListLoaded = videoList.length() > 0
    var currentVideoObject =
        if (videoListLoaded) videoList.getJSONObject(currentVideoIndex) else null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Video Player") }
            )
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (videoListLoaded && currentVideoObject != null) {
                    VideoPlayer(mainViewModel)
                }
            }
        }
    )
}

@Composable
fun VideoPlayer(mainViewModel: MainViewModel) {
    val localContext = LocalContext.current
    val playbackStateListener: Player.Listener = playbackStateListener()

    //TODO Create and add playbackStackListener to update description according to playing video
    var description = ""

    var videoList by remember { mainViewModel.videoList }
    val mExoPlayer = remember(localContext) {
        ExoPlayer.Builder(localContext).build().apply {
            for (i in 0 until videoList.length()) {
                var videoObject = videoList.getJSONObject(i)
                description = videoObject["description"] as String
                val mediaItem = MediaItem.Builder()
                    .setUri(Uri.parse(videoObject["hlsURL"] as String))
                    .build()
                addMediaItem(mediaItem)
            }
            playWhenReady = false
            prepare()
        }
    }
    mExoPlayer.addListener(playbackStateListener)
    AndroidView(factory = { context ->
        StyledPlayerView(context).apply {
            player = mExoPlayer
        }
    })
    VideoDescription(description)
}

@Composable
fun VideoDescription(description: String) {
    // ControlRow()
    MarkdownText(
        markdown = description,
        fontSize = 20.sp,
        modifier = Modifier
            .padding(20.dp, 0.dp),
    )
}

private fun playbackStateListener() = object : Player.Listener {
    val TAG = "playbackStateListener"
    override fun onPlaybackStateChanged(playbackState: Int) {
        val stateString: String = when (playbackState) {
            ExoPlayer.STATE_IDLE -> "ExoPlayer.STATE_IDLE      -"
            ExoPlayer.STATE_BUFFERING -> "ExoPlayer.STATE_BUFFERING -"
            ExoPlayer.STATE_READY -> "ExoPlayer.STATE_READY     -"
            ExoPlayer.STATE_ENDED -> "ExoPlayer.STATE_ENDED     -"
            else -> "UNKNOWN_STATE             -"
        }
        Log.d(TAG, "changed state to $stateString")
    }
}

@Composable
fun ControlRow() {
    Row(
        Modifier
            .padding(40.dp, 0.dp)
            .fillMaxWidth()
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_previous),
            contentDescription = "Previous",
            modifier = Modifier
                .padding(0.dp, 20.dp)
                .size(30.dp)
        )
        Spacer(Modifier.weight(1f))
        Image(
            imageVector = ImageVector.vectorResource(id = if (true) R.drawable.ic_play else R.drawable.ic_pause),
            contentDescription = "Play or Pause",
            modifier = Modifier
                .padding(0.dp, 20.dp)
                .size(30.dp)
        )
        Spacer(Modifier.weight(1f))
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_play_next),
            contentDescription = "Next",
            modifier = Modifier
                .padding(0.dp, 20.dp)
                .size(30.dp)
        )
    }
}

package com.silverorange.videoplayer

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
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
                    VideoPlayer(currentVideoObject)
                    VideoDescription(currentVideoObject)
                }
            }
        }
    )
}

@Composable
fun VideoPlayer(currentVideoObject: JSONObject) {
    val localContext = LocalContext.current
    val mExoPlayer = remember(localContext) {
        ExoPlayer.Builder(localContext).build().apply {
            val mediaItem = MediaItem.Builder()
                .setUri(Uri.parse(currentVideoObject["hlsURL"] as String))
                .build()
            setMediaItem(mediaItem)
            playWhenReady = true
            prepare()
        }
    }
    AndroidView(factory = { context ->
        StyledPlayerView(context).apply {
            player = mExoPlayer
        }
    })
}

@Composable
fun VideoDescription(currentVideoObject: JSONObject) {
    ControlRow()
    MarkdownText(
        markdown = currentVideoObject["description"].toString(),
        fontSize = 20.sp
    )
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

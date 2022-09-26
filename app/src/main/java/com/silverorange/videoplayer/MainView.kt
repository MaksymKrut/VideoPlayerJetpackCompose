package com.silverorange.videoplayer

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import dev.jeziellago.compose.markdowntext.MarkdownText
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


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
    val eventsListener: Player.Listener = eventsListener(mainViewModel)

    var videoList by remember { mainViewModel.videoList }
    videoList = sortVideoList(videoList)

    val mExoPlayer = remember(localContext) {
        ExoPlayer.Builder(localContext).build().apply {
            for (i in 0 until videoList.length()) {
                val videoObject = videoList.getJSONObject(i)
                val mediaItem = MediaItem.Builder()
                    .setUri(Uri.parse(videoObject["hlsURL"] as String))
                    .build()
                addMediaItem(mediaItem)
            }
            playWhenReady = false
            prepare()
        }
    }
    mExoPlayer.addListener(eventsListener)
    AndroidView(factory = { context ->
        StyledPlayerView(context).apply {
            setShowRewindButton(false)
            setShowFastForwardButton(false)
            player = mExoPlayer
        }
    })
    VideoDescription(mainViewModel)
}

@Composable
fun VideoDescription(mainViewModel: MainViewModel) {
    var videoList by remember { mainViewModel.videoList }
    var currentVideoIndex by remember { mainViewModel.currentVideoIndex }
    InfoRow(videoList.getJSONObject(currentVideoIndex))
    MarkdownText(
        markdown = videoList.getJSONObject(currentVideoIndex)["description"].toString(),
        fontSize = 20.sp,
        modifier = Modifier
            .padding(20.dp, 0.dp),
    )
}

@Composable
fun InfoRow(currentObject: JSONObject) {
    val author = currentObject.getJSONObject("author")["name"]

    Text(
        text = "Title: " + currentObject["title"] as String,
        style = TextStyle(textDecoration = TextDecoration.Underline),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
    Text(
        text = "Author: " + author as String,
        fontSize = 20.sp
    )
}

private fun eventsListener(mainViewModel: MainViewModel) = object : Player.Listener {
    override fun onEvents(player: Player, events: Player.Events) {
        mainViewModel.currentVideoIndex.value = player.getCurrentWindowIndex()
        Log.d("eventsListener", "${player.getCurrentWindowIndex()}")
    }
}

@SuppressLint("SimpleDateFormat")
private fun sortVideoList(videoListArray: JSONArray): JSONArray {
    val sortedVideoListArrayArray = JSONArray()
    val videoList: MutableList<JSONObject> = ArrayList()
    for (i in 0 until videoListArray.length()) {
        videoList.add(videoListArray.getJSONObject(i))
    }
    videoList.sortWith { p0, p1 ->
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        var valA = Date()
        var valB = Date()
        try {
            valA = format.parse(p0.get("publishedAt") as String) as Date
            valB = format.parse(p1.get("publishedAt") as String) as Date
        } catch (e: JSONException) {
            throw Exception(e)
        }
        valA.compareTo(valB)
    }
    for (i in 0 until videoList.size) {
        sortedVideoListArrayArray.put(videoList[i])
    }
    return sortedVideoListArrayArray
}


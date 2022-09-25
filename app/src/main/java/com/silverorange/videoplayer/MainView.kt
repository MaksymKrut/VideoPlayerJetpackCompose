package com.silverorange.videoplayer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
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
    var currentAuthorObject = currentVideoObject.getJSONObject("author")
    Text(text = "Current video name: ${currentAuthorObject["name"]}")
}

@Composable
fun VideoDescription(currentVideoObject: JSONObject) {
    Text(text = "Current video title: ${currentVideoObject["title"]}")
}

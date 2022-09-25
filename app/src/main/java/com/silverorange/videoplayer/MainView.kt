package com.silverorange.videoplayer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*

@Composable
fun TopBar() {
    TopAppBar(title = { Text(text = "Video Player") })
}

@Composable
fun VideoCard(mainViewModel: MainViewModel) {
    var videoList by remember { mainViewModel.videoList }
    var currentVideoIndex by remember { mainViewModel.currentVideoIndex }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "List length: ${videoList.length()}")
    }
}

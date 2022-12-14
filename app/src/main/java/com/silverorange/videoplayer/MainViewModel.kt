package com.silverorange.videoplayer

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray


class MainViewModel(app: Application) : AndroidViewModel(app), LifecycleObserver {
    @SuppressLint("StaticFieldLeak")
    val appContext: Context = getApplication<Application>().applicationContext

    @SuppressLint("MutableCollectionMutableState")
    var videoList = mutableStateOf(JSONArray("[]"))
    var currentVideoIndex = mutableStateOf(0)

    init {
        CoroutineScope(Dispatchers.IO).launch {
            getVideoList()
        }
    }

    private fun getVideoList() {
        val url = "http://192.168.10.125:4000/videos"
        url.httpGet().responseJson() { _, _, result ->
            val response = result.get()
            videoList.value = response.array()
            println("Response videoList.value:${videoList.value}")
        }
    }
}
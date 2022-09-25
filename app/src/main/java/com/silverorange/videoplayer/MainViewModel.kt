package com.silverorange.videoplayer

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app), LifecycleObserver {
    private val TAG = "MainViewModel"
    @SuppressLint("StaticFieldLeak")
    val appContext: Context = getApplication<Application>().applicationContext
    var videoList = mutableStateOf(listOf(ArrayList<String>()))

    init {
        CoroutineScope(Dispatchers.IO).launch {
//            videoList = try {
//                // getVideoList()
//            } catch (exception: Exception) {
//                // show error toast
//            }
        }
    }

}
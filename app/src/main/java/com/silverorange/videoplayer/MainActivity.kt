package com.silverorange.videoplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider

class MainActivity : ComponentActivity(), LifecycleOwner {
    private val TAG = "MainActivity"
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        lifecycle.addObserver(mainViewModel)
        setContent {
            VideoScreen(mainViewModel)
        }
    }
}

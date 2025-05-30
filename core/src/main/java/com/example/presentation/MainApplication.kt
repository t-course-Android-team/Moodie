package com.example.presentation

import android.app.Application
import com.example.data.WatchedMoviesDataBase

class MainApplication : Application() {
    val database by lazy { WatchedMoviesDataBase.WatchedMoviesDataBase.getWatchedDataBase(this) }
}
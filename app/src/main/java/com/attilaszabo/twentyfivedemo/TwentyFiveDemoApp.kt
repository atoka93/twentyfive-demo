package com.attilaszabo.twentyfivedemo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TwentyFiveDemoApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}

package com.attilaszabo.twentyfivedemo

import android.app.Application
import com.attilaszabo.twentyfivedemo.di.AppDiModule

class TwentyFiveDemoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDiModule.initialize(this@TwentyFiveDemoApp)
    }
}

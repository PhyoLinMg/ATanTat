package com.elemental.atantat

import android.app.Application
import me.myatminsoe.mdetect.MDetect

class AtantatApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        MDetect.init(this)

    }
}
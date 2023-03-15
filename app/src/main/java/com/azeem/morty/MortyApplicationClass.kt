package com.azeem.morty

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class MortyApplicationClass: Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}
package com.task.noteapp

import android.app.Application
import com.task.noteapp.di.AppComponent
import com.task.noteapp.di.DaggerAppComponent

class App: Application() {

    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    fun appComponent() = component
}
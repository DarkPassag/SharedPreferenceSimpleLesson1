package com.ch.ni.an.sharedpreferencesimplelesson

import android.app.Application
import com.ch.ni.an.sharedpreferencesimplelesson.model.UserService

class App: Application() {
    val userService = UserService()
}
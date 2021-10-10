package com.ch.ni.an.sharedpreferencesimplelesson

import com.ch.ni.an.sharedpreferencesimplelesson.model.User

interface Navigator {

    fun showDetails(user: User)

    fun goBack()

    fun toast(messageRes: Int)
}
package com.ch.ni.an.sharedpreferencesimplelesson.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ch.ni.an.sharedpreferencesimplelesson.UserNotFoundExceptions
import com.ch.ni.an.sharedpreferencesimplelesson.model.User
import com.ch.ni.an.sharedpreferencesimplelesson.model.UserDetails
import com.ch.ni.an.sharedpreferencesimplelesson.model.UserService

class UserDetailsViewModel(
  private val  userService: UserService
): ViewModel() {

    private val _userDetails = MutableLiveData<UserDetails>()
    val userDetails: LiveData<UserDetails> = _userDetails

    fun loadUser(userId: Long){
        if(_userDetails.value != null) return
        try {
            _userDetails.value = userService.getById(userId)
        } catch (e: UserNotFoundExceptions){
            e.printStackTrace()
        }

    }

    fun deleteUser(){
        val userDetails = this.userDetails.value ?: return
        userService.deleteUser(userDetails.user)
    }
}
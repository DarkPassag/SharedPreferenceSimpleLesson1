package com.ch.ni.an.sharedpreferencesimplelesson.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ch.ni.an.sharedpreferencesimplelesson.model.User
import com.ch.ni.an.sharedpreferencesimplelesson.model.UserService
import com.ch.ni.an.sharedpreferencesimplelesson.model.UsersListener

class UsersListViewModel(
    private val userService: UserService
): ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users
    private val listener: UsersListener = {
        _users.value = it
    }

    init {
        loadUsers()
    }

    override fun onCleared() {
        super.onCleared()
        userService.removeListener(listener)
    }

    fun loadUsers(){
        userService.addListener(listener)
    }

    fun moveUser(user:User, moveBy: Int){
        userService.moveUser(user, moveBy)
    }

    fun deleteUser(user: User){
        userService.deleteUser(user)
    }


}
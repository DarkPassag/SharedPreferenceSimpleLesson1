package com.ch.ni.an.sharedpreferencesimplelesson.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ch.ni.an.sharedpreferencesimplelesson.model.User
import com.ch.ni.an.sharedpreferencesimplelesson.model.UserService
import com.ch.ni.an.sharedpreferencesimplelesson.model.UsersListener
import com.ch.ni.an.sharedpreferencesimplelesson.tasks.*


data class UserListItem(
    val user: User,
    val isInProgress: Boolean
)


class UsersListViewModel(
    private val userService: UserService
): BaseViewModel() {

    private val _users = MutableLiveData<Result<List<UserListItem>>>()
    val users: LiveData<Result<List<UserListItem>>> = _users

    private val userIdInProgress = mutableSetOf<Long>()
    private var userResult: Result<List<User>> = EmptyResult()
    set(value) {
        field = value
        notifyUpdates()
    }
    private val listener: UsersListener = {
       userResult = if(it.isEmpty()) {
            EmptyResult()
        } else SuccessResult(it)
    }

    init {
        userService.addListener(listener)
        loadUsers()
    }

    override fun onCleared() {
        super.onCleared()
        userService.removeListener(listener)
    }

    fun loadUsers(){
        userResult = PendingResult()
        userService.loadUsers()
            .onError { userResult = ErrorResult(it) }.autoCancel()
    }

    fun moveUser(user:User, moveBy: Int){
        if(isInProgress(user)) return
        addProgressTo(user)
        userService.moveUser(user, moveBy)
            .onSuccess {
                removeProgressFrom(user)
            }
            .onError { removeProgressFrom(user) }.autoCancel()
    }

    fun deleteUser(user: User){
        if(isInProgress(user)) return
        addProgressTo(user)
        userService.deleteUser(user)
            .onSuccess { removeProgressFrom(user) }
            .onError { removeProgressFrom(user) }.autoCancel()
    }

    private fun addProgressTo(user: User){
        userIdInProgress.add(user.id)
        notifyUpdates()
    }

    private fun removeProgressFrom(user: User){
        userIdInProgress.remove(user.id)
        notifyUpdates()
    }

    private fun isInProgress(user: User): Boolean{
        return userIdInProgress.contains(user.id)
    }

    private fun notifyUpdates(){
        _users.postValue(userResult.map { users ->
            users.map { user -> UserListItem(user, isInProgress(user) ) }
        })

    }


}
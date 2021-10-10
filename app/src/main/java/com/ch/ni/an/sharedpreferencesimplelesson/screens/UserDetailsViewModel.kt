package com.ch.ni.an.sharedpreferencesimplelesson.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ch.ni.an.sharedpreferencesimplelesson.UserNotFoundExceptions
import com.ch.ni.an.sharedpreferencesimplelesson.model.User
import com.ch.ni.an.sharedpreferencesimplelesson.model.UserDetails
import com.ch.ni.an.sharedpreferencesimplelesson.model.UserService
import com.ch.ni.an.sharedpreferencesimplelesson.tasks.EmptyResult
import com.ch.ni.an.sharedpreferencesimplelesson.tasks.PendingResult
import com.ch.ni.an.sharedpreferencesimplelesson.tasks.Result
import com.ch.ni.an.sharedpreferencesimplelesson.tasks.SuccessResult

class UserDetailsViewModel(
  private val  userService: UserService
): BaseViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private val currentState: State get()= _state.value!!

    init {
        _state.value = State(
            userDetailsResult = EmptyResult(),
            deleringInProgress = false
        )
    }

    fun loadUser(userId: Long){
        if(currentState.userDetailsResult is SuccessResult) return
        _state.value = currentState.copy(userDetailsResult = PendingResult())

        userService.getById(userId)
            .onSuccess {
                _state.value = currentState.copy(userDetailsResult = SuccessResult(it))
            }.onError {
                //todo
            }.autoCancel()
    }


    fun deleteUser(){
       val userDetailsResult = currentState.userDetailsResult
        if(userDetailsResult !is SuccessResult) return
        _state.value = currentState.copy(deleringInProgress = true)
        userService.deleteUser(userDetailsResult.data.user)
            .onSuccess {
                //todo
            }
            .onError {
                _state.value = currentState.copy(deleringInProgress = false)
            }
    }

    data class State(
        val userDetailsResult: Result<UserDetails>,
        private val deleringInProgress: Boolean
    ){
        val showContent: Boolean get() = userDetailsResult is SuccessResult
        val showProgress:Boolean get() = userDetailsResult is PendingResult || deleringInProgress
        val enableDeleteButton: Boolean get() = !deleringInProgress
    }
}
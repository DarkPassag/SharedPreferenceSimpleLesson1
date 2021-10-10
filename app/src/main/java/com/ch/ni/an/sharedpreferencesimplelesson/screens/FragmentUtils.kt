package com.ch.ni.an.sharedpreferencesimplelesson.screens

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch.ni.an.sharedpreferencesimplelesson.App
import com.ch.ni.an.sharedpreferencesimplelesson.Navigator
import java.lang.IllegalStateException

class ViewModelFactory(
    private val app: com.ch.ni.an.sharedpreferencesimplelesson.App
): ViewModelProvider.Factory{


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when(modelClass){
            UsersListViewModel::class.java -> {
                UsersListViewModel(app.userService)
            }
            UserDetailsViewModel::class.java -> {
                UserDetailsViewModel(app.userService)
            }
            else -> { throw IllegalStateException("Unknow viewModel class")}
        }
        return viewModel as T
    }

}

fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)

fun Fragment.navigator() = requireContext() as Navigator
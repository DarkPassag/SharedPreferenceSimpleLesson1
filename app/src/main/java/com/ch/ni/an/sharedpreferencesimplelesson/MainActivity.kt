package com.ch.ni.an.sharedpreferencesimplelesson

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.ch.ni.an.sharedpreferencesimplelesson.databinding.ActivityMainBinding
import com.ch.ni.an.sharedpreferencesimplelesson.model.User
import com.ch.ni.an.sharedpreferencesimplelesson.model.UserService
import com.ch.ni.an.sharedpreferencesimplelesson.model.UsersListener

class MainActivity : BaseActivity() {

    private val userService: UserService
        get() = (applicationContext as App).userService

    private lateinit var bind: ActivityMainBinding
    private lateinit var adapter: UsersAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        adapter= UsersAdapter(object : UserActionListener {
            override fun onUserMove(user: User, int: Int) {
               userService.moveUser(user, int)
            }

            override fun onUserDelete(user: User) {
                userService.deleteUser(user)
            }

            override fun onUserDetails(user: User) {
                Toast.makeText(this@MainActivity, "User: ${user.name}", Toast.LENGTH_SHORT).show()
            }

            override fun onUserFire(user: User) {
                userService.fireUser(user)
            }

        })
        val layoutManager = LinearLayoutManager(this)
        bind.recyclerView.layoutManager = layoutManager
        bind.recyclerView.adapter = adapter
        val itemAnimator = bind.recyclerView.itemAnimator
        if(itemAnimator is DefaultItemAnimator){
            itemAnimator.supportsChangeAnimations = false
        }

        userService.addListener (usersListener)


    }

    override fun onDestroy() {
        super.onDestroy()
        userService.removeListener(usersListener)
    }

    private val usersListener: UsersListener = {
        adapter.users = it
    }
}
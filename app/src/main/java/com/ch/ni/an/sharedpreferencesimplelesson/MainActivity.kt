package com.ch.ni.an.sharedpreferencesimplelesson

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.ch.ni.an.sharedpreferencesimplelesson.databinding.ActivityMainBinding
import com.ch.ni.an.sharedpreferencesimplelesson.model.User
import com.ch.ni.an.sharedpreferencesimplelesson.model.UserService
import com.ch.ni.an.sharedpreferencesimplelesson.model.UsersListener
import com.ch.ni.an.sharedpreferencesimplelesson.screens.UserDetailFragment
import com.ch.ni.an.sharedpreferencesimplelesson.screens.UsersListFragment

class MainActivity : BaseActivity(), Navigator {

    private lateinit var bind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        if(savedInstanceState ==null)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, UsersListFragment(), null)
            .commit()
    }

    override fun showDetails(user: User) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.fragmentContainer,
                UserDetailFragment.newInstance(user.id),
                null)
            .commit()
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun toast(messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }

}
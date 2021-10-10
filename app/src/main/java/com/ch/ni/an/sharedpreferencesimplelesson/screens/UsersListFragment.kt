package com.ch.ni.an.sharedpreferencesimplelesson.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ch.ni.an.sharedpreferencesimplelesson.UserActionListener
import com.ch.ni.an.sharedpreferencesimplelesson.UsersAdapter
import com.ch.ni.an.sharedpreferencesimplelesson.databinding.FragmentUsersListBinding
import com.ch.ni.an.sharedpreferencesimplelesson.model.User

class UsersListFragment: Fragment() {
    private lateinit var bind: FragmentUsersListBinding
    private lateinit var adapter: UsersAdapter
    private val viewModel: UsersListViewModel by viewModels{ factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentUsersListBinding.inflate(inflater, container, false)
        adapter = UsersAdapter(object : UserActionListener {
            override fun onUserMove(user: User, int: Int) {
                viewModel.moveUser(user, int)
            }

            override fun onUserDelete(user: User) {
                viewModel.deleteUser(user)
            }

            override fun onUserDetails(user: User) {
                navigator().showDetails(user)
            }

            override fun onUserFire(user: User) {
                //todo
            }

        })

        viewModel.users.observe(viewLifecycleOwner, {
            adapter.users = it
        })

        val layoutManager = LinearLayoutManager(requireContext())
        bind.recyclerView.layoutManager = layoutManager
        bind.recyclerView.adapter = adapter

        return bind.root
    }

}
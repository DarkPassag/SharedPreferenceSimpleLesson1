package com.ch.ni.an.sharedpreferencesimplelesson.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.ch.ni.an.sharedpreferencesimplelesson.R
import com.ch.ni.an.sharedpreferencesimplelesson.databinding.FragmentUserDetailsBinding

class UserDetailFragment: Fragment() {
    private lateinit var bind: FragmentUserDetailsBinding
    private val viewModel: UserDetailsViewModel by viewModels {factory()}


    companion object {
        private const val ARG_USER_ID = "ARG_USER_ID"

        fun newInstance(userId: Long): UserDetailFragment {
            val fragment = UserDetailFragment()
            fragment.arguments = bundleOf(ARG_USER_ID to userId)
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadUser(requireArguments().getLong(ARG_USER_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentUserDetailsBinding.inflate(inflater, container, false)

        viewModel.userDetails.observe(viewLifecycleOwner, {
            bind.userNameTextView.text = it.user.name
            bind.userDetailsTextView.text = it.details
            if(it.user.photo.isNotBlank()){
                Glide.with(this)
                    .load(it.user.photo)
                    .circleCrop()
                    .into(bind.photoImageView)
            } else {
                Glide.with(this)
                    .load(R.drawable.ic_user_avatar)
                    .into(bind.photoImageView)
            }
        })


        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.deleteButton.setOnClickListener{
            viewModel.deleteUser()
            navigator().toast(R.string.user_deleted)
            navigator().goBack()
        }
    }
}
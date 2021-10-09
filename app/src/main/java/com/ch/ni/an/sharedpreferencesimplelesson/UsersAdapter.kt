package com.ch.ni.an.sharedpreferencesimplelesson

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.ch.ni.an.sharedpreferencesimplelesson.databinding.ItemUserBinding
import com.ch.ni.an.sharedpreferencesimplelesson.model.User



interface UserActionListener{
    fun onUserMove(user: User, int: Int)

    fun onUserDelete(user: User)

    fun onUserDetails(user:User)

    fun onUserFire(user: User)
}

class UserDiffCallback(
    private val oldList: List<User>,
    private val newList: List<User>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return  oldUser == newUser
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser.id == newUser.id
    }
}

class UsersAdapter(
    private val actionListener: UserActionListener
    ): RecyclerView.Adapter<UsersHolder>(), View.OnClickListener {






    var users : List<User> = emptyList()
    set(newValue)  {
        val diffCallback = UserDiffCallback(field, newValue)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        field = newValue
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onClick(v: View) {
        val user = v.tag as User
        when(v.id){
            R.id.moreImage -> {
                showPopUPMenu(v)
            }
            else -> {
                actionListener.onUserDetails(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bind = ItemUserBinding.inflate(inflater, parent, false)

        bind.root.setOnClickListener(this)
        bind.moreImage.setOnClickListener(this)

        return UsersHolder(bind)
    }

    override fun onBindViewHolder(holder: UsersHolder, position: Int) {
        val user = users[position]
        val context = holder.itemView.context
        with(holder.bind){
            holder.itemView.tag = user
            moreImage.tag = user
            userCompanyTextView.text = if(user.company.isNotBlank()) user.company else
                context.getString(R.string.unemployed)
            userNameTextView.text = user.name
            if(user.photo.isNotBlank()){
                Glide.with(photoImageView.context)
                    .load(user.photo)
                    .circleCrop()
                    .placeholder(R.drawable.ic_avatar)
                    .error(R.drawable.ic_avatar)
                    .into(photoImageView)
            } else {
                photoImageView.setImageResource(R.drawable.ic_avatar)
            }

        }
    }

    private fun showPopUPMenu(v: View){
        val popUpMenu = PopupMenu(v.context, v)
        val context = v.context
        val user = v.tag as User
        val position = users.indexOfFirst { it.id == user.id }
        popUpMenu.menu.add(0, ID_MOVE_UP, Menu.NONE, context.getString(R.string.move_up)).apply {
            isEnabled = position > 0

        }
        popUpMenu.menu.add(0, ID_MOVE_DOWN, Menu.NONE, context.getString(R.string.move_down)).apply {
            isEnabled = position < users.size -1

        }
        popUpMenu.menu.add(0, ID_MOVE_REMOVE, Menu.NONE, context.getString(R.string.move_remove)).apply {

        }

        if(user.company.isNotBlank()){
            popUpMenu.menu.add(0, ID_MOVE_FIRE, Menu.NONE, context.getString(R.string.fire))
        }


        popUpMenu.setOnMenuItemClickListener {
            when(it.itemId){
                ID_MOVE_UP -> {
                    actionListener.onUserMove(user, -1)
                }
                ID_MOVE_DOWN -> {
                    actionListener.onUserMove(user, 1)
                }
                ID_MOVE_REMOVE -> { actionListener.onUserDelete(user)
                }

                ID_MOVE_FIRE -> {
                    actionListener.onUserFire(user)
                }
            }
            return@setOnMenuItemClickListener true
        }

        popUpMenu.show()
    }

    override fun getItemCount(): Int = users.size

    companion object{
        private const val ID_MOVE_UP = 0
        private const val ID_MOVE_DOWN = 1
        private const val ID_MOVE_REMOVE = 3
        private const val ID_MOVE_FIRE = 4
    }
}




